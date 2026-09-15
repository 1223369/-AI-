package sparkx.sparkshop.knowledge.validate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 问题新增/编辑参数
 */
@Data
@Schema(description = "问题新增/编辑参数")
public class QuestionValidate implements Serializable {

    @Schema(description = "问题 id（编辑时必填）")
    private Long id;

    @Schema(description = "知识库 id（新增时必填）")
    @NotBlank(message = "知识库 id 不能为空")
    private String kbId;

    @Schema(description = "问题文本")
    @NotBlank(message = "问题内容不能为空")
    private String content;
}
