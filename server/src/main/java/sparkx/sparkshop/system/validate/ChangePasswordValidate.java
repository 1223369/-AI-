package sparkx.sparkshop.system.validate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;

/**
 * 修改密码参数
 */
@Data
public class ChangePasswordValidate implements Serializable {

    /**
     * 原密码
     */
    @Schema(description = "原密码")
    @NotEmpty(message = "请输入原密码")
    private String oldPwd;

    /**
     * 新密码
     */
    @Schema(description = "新密码")
    @NotEmpty(message = "请输入新密码")
    private String newPwd;
}
