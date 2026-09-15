package sparkx.sparkshop.knowledge.validate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 意图分类单条实时评估入参。
 */
@Data
@Schema(description = "意图分类单条实时评估入参")
public class IntentEvalSingleValidate implements Serializable {

    @Schema(description = "用户问题")
    private String query;

    @Schema(description = "保留前 N 候选（不传默认 3）")
    private Integer topN;

    @Schema(description = "最小分数阈值（不传默认 0.35）")
    private Double minScore;
}
