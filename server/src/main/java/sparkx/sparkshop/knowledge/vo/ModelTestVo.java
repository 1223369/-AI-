package sparkx.sparkshop.knowledge.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 模型连通性测试结果 VO
 */
@Data
@Schema(description = "模型连通性测试结果")
public class ModelTestVo implements Serializable {

    @Schema(description = "是否成功")
    private Boolean success;

    @Schema(description = "提示信息")
    private String message;

    @Schema(description = "耗时（毫秒）")
    private Long latencyMs;
}
