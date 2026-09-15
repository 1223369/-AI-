package sparkx.sparkshop.knowledge.validate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 触发知识图谱抽取参数。
 */
@Data
@Schema(description = "触发知识图谱抽取参数")
public class KgExtractValidate implements Serializable {

    @Schema(description = "知识库 id")
    @NotBlank(message = "知识库 id 不能为空")
    private String kbId;

    @Schema(description = "指定文档 id 列表（可选，为空则对该 KB 下所有未抽取的文档补抽取）")
    private List<String> documentIds;
}
