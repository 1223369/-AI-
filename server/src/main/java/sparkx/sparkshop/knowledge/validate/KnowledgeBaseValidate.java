package sparkx.sparkshop.knowledge.validate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 新增/编辑知识库参数
 */
@Data
@Schema(description = "新增/编辑知识库参数")
public class KnowledgeBaseValidate implements Serializable {

    @Schema(description = "知识库 id（编辑时必填）")
    private String id;

    @Schema(description = "知识库名称")
    @NotBlank(message = "知识库名称不能为空")
    private String name;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "嵌入模型 id（新增时必填，确定服务地址/凭证）")
    @NotNull(message = "嵌入模型不能为空")
    private Integer embeddingModelId;

    @Schema(description = "具体模型名（ai_model.models 里的某一项；为空时后端取首项）")
    private String embeddingModelName;

    @Schema(description = "状态 1正常 2禁用")
    private Integer status;
}
