package sparkx.sparkshop.knowledge.validate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 智能体测试对话会话入参（新建/编辑）。
 */
@Data
@Schema(description = "测试对话会话入参")
public class AgentTestSessionSaveValidate implements Serializable {

    @Schema(description = "会话标题（可选，空则默认「新会话」）")
    private String title;

    @Schema(description = "智能体 id（新建时必传）")
    private String agentId;
}
