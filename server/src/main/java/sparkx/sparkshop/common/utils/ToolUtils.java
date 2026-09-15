package sparkx.sparkshop.common.utils;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;

import java.time.LocalDateTime;

/**
 * 工具方法：密码加密、时间
 */
public class ToolUtils {

    private ToolUtils() {
    }

    /**
     * 生成密码：md5(md5(password + salt) + salt)
     * 与前端/PHP 端 makePassword 保持同一算法即可校验通过
     */
    public static String makePassword(String password, String salt) {
        return SecureUtil.md5(SecureUtil.md5(password + salt) + salt);
    }

    /**
     * 校验密码
     */
    public static boolean verifyPassword(String dbPassword, String password, String salt) {
        return dbPassword != null && dbPassword.equals(makePassword(password, salt));
    }

    /**
     * 生成随机盐（simpleUUID，32 位无横线）
     */
    public static String makeSalt() {
        return IdUtil.simpleUUID();
    }

    /**
     * 当前时间
     */
    public static LocalDateTime nowDateTime() {
        return LocalDateTime.now();
    }
}
