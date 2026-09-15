package sparkx.sparkshop.knowledge.vo;

import com.fasterxml.jackson.annotation.JsonRawValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 智能体测试对话消息展示 VO。references/stageData/stageTimings 用 @JsonRawValue 原样输出为 JSON。
 */
@Data
@Schema(description = "测试对话消息")
public class AgentTestMessageVo implements Serializable {

    @Schema(description = "消息 id")
    private Long id;

    @Schema(description = "角色 user / assistant")
    private String role;

    @Schema(description = "消息内容")
    private String content;

    @JsonRawValue
    @Schema(description = "引用来源 JSON")
    private String references;

    @JsonRawValue
    @Schema(description = "RAG 各阶段上下文 JSON")
    private String stageData;

    @JsonRawValue
    @Schema(description = "RAG 各阶段耗时 JSON")
    private String stageTimings;

    @Schema(description = "总耗时（毫秒）")
    private Long totalCost;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
}
