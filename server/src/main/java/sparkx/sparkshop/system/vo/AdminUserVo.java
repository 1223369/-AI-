package sparkx.sparkshop.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 管理员列表项（含关联字段，字段统一驼峰）
 */
@Data
public class AdminUserVo implements Serializable {

    @Schema(description = "管理员 id")
    private Integer id;

    @Schema(description = "登录账号")
    private String account;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "状态 1:正常 2:禁用")
    private Integer status;

    @Schema(description = "最近登录 ip")
    private String lastLoginIp;

    @Schema(description = "最近登录时间")
    private String lastLoginTime;

    @Schema(description = "创建时间")
    private String createTime;

    @Schema(description = "更新时间")
    private String updateTime;
}
