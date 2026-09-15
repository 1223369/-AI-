package sparkx.sparkshop.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import sparkx.sparkshop.common.exception.BusinessException;
import sparkx.sparkshop.common.utils.JwtUtils;
import sparkx.sparkshop.common.utils.ToolUtils;
import sparkx.sparkshop.system.entity.AdminUser;
import sparkx.sparkshop.system.mapper.AdminUserMapper;
import sparkx.sparkshop.system.service.ILoginService;
import sparkx.sparkshop.system.validate.LoginValidate;
import sparkx.sparkshop.system.vo.LoginReturnVo;
import sparkx.sparkshop.system.vo.SystemUserVo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

/**
 * 登录服务：账号密码校验、签发 token。
 * <p>
 * 菜单已改为前端静态写死（{@code admin/src/router/staticMenus.ts}），不再下发动态菜单；
 * 角色体系已移除，所有登录用户权限一致。
 */
@Slf4j
@Service
public class LoginServiceImpl implements ILoginService {

    @Resource
    private AdminUserMapper adminUserMapper;

    @Resource
    private HttpServletRequest request;

    @Value("${sparkx.jwt-secret}")
    private String jwtSecret;

    @Value("${sparkx.jwt-expire}")
    private Long jwtExpireHours;

    /**
     * 执行登录：查用户 → 校验密码与状态 → 更新登录信息 → 签发 token。
     * 菜单前端静态写死，这里不再下发。
     *
     * @param validate 登录参数（账号、密码）
     * @return 登录返回结构
     */
    @Override
    public LoginReturnVo doLogin(LoginValidate validate) {
        AdminUser user = adminUserMapper.selectOne(new LambdaQueryWrapper<AdminUser>()
                .eq(AdminUser::getAccount, validate.getUsername())
                .last("limit 1"));
        if (user == null) {
            throw new BusinessException("账号密码错误");
        }
        if (!ToolUtils.verifyPassword(user.getPassword(), validate.getPassword(), user.getSalt())) {
            throw new BusinessException("账号密码错误");
        }
        if (user.getStatus() != null && user.getStatus() == 2) {
            throw new BusinessException("该账号已被禁用");
        }

        AdminUser update = new AdminUser();
        update.setId(user.getId());
        update.setLastLoginIp(getClientIp());
        update.setLastLoginTime(LocalDateTime.now());
        adminUserMapper.updateById(update);

        SystemUserVo userInfo = new SystemUserVo(
                user.getNickname(), user.getAccount(), user.getId(),
                null, "", user.getAvatar());

        long expireAt = System.currentTimeMillis() / 1000 + jwtExpireHours * 3600;
        String token = JwtUtils.create(jwtSecret, user.getId(), null,
                user.getNickname(), expireAt);

        LoginReturnVo vo = new LoginReturnVo();
        vo.setToken(token);
        vo.setUserInfo(userInfo);
        vo.setMenu(Collections.emptyList());
        return vo;
    }

    /**
     * 获取客户端真实 IP，依次取 X-Forwarded-For、X-Real-IP、RemoteAddr
     *
     * @return 客户端 IP
     */
    private String getClientIp() {
        if (request == null) {
            return "";
        }
        String ip = request.getHeader("X-Forwarded-For");
        if (StrUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (StrUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
