package sparkx.sparkshop.system.validate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 管理员列表查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AdminUserSearchValidate extends PageValidate implements Serializable {

    /**
     * 昵称（模糊搜索）
     */
    @Schema(description = "昵称（模糊搜索）")
    private String nickname;

    /**
     * 账号（模糊搜索）
     */
    @Schema(description = "账号（模糊搜索）")
    private String account;
}
