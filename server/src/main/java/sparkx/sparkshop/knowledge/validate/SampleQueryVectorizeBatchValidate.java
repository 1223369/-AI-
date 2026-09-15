package sparkx.sparkshop.knowledge.validate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 样例查询批量向量化入参（ids 为空表示全部启用项）。
 */
@Data
@Schema(description = "样例查询批量向量化入参")
public class SampleQueryVectorizeBatchValidate implements Serializable {

    @Schema(description = "待向量化的样例 id 列表（为空表示全部启用项）")
    private List<Long> ids;
}
