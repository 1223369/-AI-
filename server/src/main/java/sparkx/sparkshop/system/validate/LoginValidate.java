package sparkx.sparkshop.system.validate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * 登录参数
 */
@Data
public class LoginValidate implements Serializable {

    /**
     * 账号
     */
    @Schema(description = "账号")
    @NotEmpty(message = "账号不能为空")
    @Size(min = 2, max = 30, message = "账号或密码错误")
    private String username;

    /**
     * 密码
     */
    @Schema(description = "密码")
    @NotEmpty(message = "密码不能为空")
    @Size(min = 4, max = 64, message = "账号或密码错误")
    private String password;

}
