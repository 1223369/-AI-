package sparkx.sparkshop.system.controller;

import sparkx.sparkshop.common.core.AjaxResult;
import sparkx.sparkshop.system.service.ILoginService;
import sparkx.sparkshop.system.validate.LoginValidate;
import sparkx.sparkshop.system.vo.LoginReturnVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "登录接口")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    private ILoginService loginService;

    /**
     * 执行登录：校验账号密码，返回 token、用户信息、动态菜单
     *
     * @param validate 登录参数（账号、密码）
     * @return 登录返回结构
     */
    @Operation(summary = "执行登录")
    @PostMapping("/doLogin")
    public AjaxResult<LoginReturnVo> doLogin(@RequestBody @Valid LoginValidate validate) {
        return AjaxResult.success(loginService.doLogin(validate));
    }
}
