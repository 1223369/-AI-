package sparkx.sparkshop.knowledge.validate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 落库单条聊天消息入参。流式回答结束后由前端调用。
 * user 消息仅填 role/content；assistant 消息可带 references/stageData/workflowSteps/cost/tokens。
 */
@Data
@Schema(description = "落库聊天消息入参")
public class ChatMessageSaveValidate implements Serializable {

    @Schema(description = "角色 user / assistant")
    private String role;

    @Schema(description = "消息内容")
    private String content;

    @Schema(description = "引用来源 JSON 字符串（assistant）")
    private String references;

    @Schema(description = "RAG 各阶段上下文 JSON 字符串（assistant）")
    private String stageData;

    @Schema(description = "编排智能体步骤 JSON 字符串（assistant）")
    private String workflowSteps;

    @Schema(description = "总耗时（毫秒）")
    private Long totalCost;

    @Schema(description = "总 token 数")
    private Integer totalTokens;
}
