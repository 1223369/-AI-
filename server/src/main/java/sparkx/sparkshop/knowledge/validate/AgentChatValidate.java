package sparkx.sparkshop.knowledge.validate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 智能体测试对话入参。
 */
@Data
@Schema(description = "智能体测试对话入参")
public class AgentChatValidate implements Serializable {

    @Schema(description = "智能体 id")
    @NotBlank(message = "智能体 id 不能为空")
    private String agentId;

    @Schema(description = "会话 id（前端生成 UUID；同 id 维持多轮记忆，留空则单轮）")
    private String conversationId;

    @Schema(description = "聊天会话 id（来自 /api/v1/sessions 的 id；落库 assistant 回复用，测试对话可不传）")
    private String sessionId;

    @Schema(description = "用户问题")
    @NotBlank(message = "问题不能为空")
    private String query;
}
