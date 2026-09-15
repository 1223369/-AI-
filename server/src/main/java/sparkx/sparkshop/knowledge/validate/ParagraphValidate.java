package sparkx.sparkshop.knowledge.validate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 段落（子块）新增/编辑参数
 */
@Data
@Schema(description = "段落新增/编辑参数")
public class ParagraphValidate implements Serializable {

    @Schema(description = "子块 id（编辑时必填）")
    private String id;

    @Schema(description = "知识库 id（新增时必填）")
    @NotBlank(message = "知识库 id 不能为空")
    private String kbId;

    @Schema(description = "文本内容")
    @NotBlank(message = "内容不能为空")
    private String content;
}
