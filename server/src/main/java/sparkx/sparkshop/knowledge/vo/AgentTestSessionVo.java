package sparkx.sparkshop.knowledge.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 智能体测试对话会话展示 VO。
 */
@Data
@Schema(description = "测试对话会话")
public class AgentTestSessionVo implements Serializable {

    @Schema(description = "会话 id")
    private String id;

    @Schema(description = "智能体 id")
    private String agentId;

    @Schema(description = "会话标题")
    private String title;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
}
