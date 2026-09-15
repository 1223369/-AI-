package sparkx.sparkshop.knowledge.validate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 编辑知识库参数（不修改嵌入模型）
 */
@Data
@Schema(description = "编辑知识库参数")
public class KnowledgeBaseEditValidate implements Serializable {

    @Schema(description = "知识库 id")
    @NotBlank(message = "知识库 id 不能为空")
    private String id;

    @Schema(description = "知识库名称")
    @NotBlank(message = "知识库名称不能为空")
    private String name;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "状态 1正常 2禁用")
    private Integer status;
}
