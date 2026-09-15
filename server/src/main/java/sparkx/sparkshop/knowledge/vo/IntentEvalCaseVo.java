package sparkx.sparkshop.knowledge.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 意图分类评估单条用例入参
 */
@Data
@Schema(description = "意图分类评估单条用例")
public class IntentEvalCaseVo implements Serializable {

    @Schema(description = "用户问题", requiredMode = Schema.RequiredMode.REQUIRED)
    private String query;

    @Schema(description = "期望命中的节点 id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String expectNodeId;

    @Schema(description = "期望命中的节点名称（仅展示用，可空）")
    private String expectNodeName;

    @Schema(description = "备注（可空）")
    private String note;
}
