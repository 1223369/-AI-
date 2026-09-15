package sparkx.sparkshop.knowledge.validate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 意图测试集 AI 生成入参。
 */
@Data
@Schema(description = "意图测试集 AI 生成入参")
public class IntentSeedGenValidate implements Serializable {

    @Schema(description = "每个意图节点生成几条（≤0 时取默认值 4）")
    private Integer countPerNode;
}
