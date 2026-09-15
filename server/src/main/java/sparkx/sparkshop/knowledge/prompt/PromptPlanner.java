package sparkx.sparkshop.knowledge.prompt;

import org.springframework.stereotype.Component;
import sparkx.sparkshop.knowledge.intent.QueryIntent;

import java.util.List;
import java.util.Map;

/**
 * 提示词规划器（文档 5.14.2）—— 意图路由 + 场景路由 + 意图级模板覆盖。
 *
 * 三层选择（优先级从高到低）：
 *  1. 意图专用模板：CHITCHAT/FOLLOW_UP 等非检索意图各有专用 .st（人设/口吻/是否引用历史各不同），
 *     覆盖场景模板。借鉴 WeKnora intent_prompts.yaml：按意图类型给差异化 system prompt。
 *  2. 场景决定基模板：KB_ONLY/MCP_ONLY/MIXED/EMPTY 各对应一个 .st（按证据来源分，与意图正交）
 *  3. 意图级节点覆盖：单意图且节点配了 promptTemplate → 完全覆盖默认模板
 *
 * 由 {@code GenerateStage} 调用构建 system prompt，再拼接证据(<documents>/<tool-data>)+问题。
 */
@Component
public class PromptPlanner {

    private final PromptTemplateLoader templateLoader;

    public PromptPlanner(PromptTemplateLoader templateLoader) {
        this.templateLoader = templateLoader;
    }

    /**
     * 构建系统提示词（带意图路由）。
     *
     * <p>优先级：意图专用模板（CHITCHAT/FOLLOW_UP）→ 意图级节点覆盖 → 场景默认。
     *
     * @param scene      提示词场景（按证据来源分）
     * @param intent     查询意图（IntentStage 判定，决定是否用意图专用模板）
     * @param intentTpls 意图级模板覆盖候选（节点配了 promptTemplate 时传）
     * @param kbContext  KB 上下文（已渲染）
     * @param mcpContext MCP 上下文（已渲染）
     * @return 系统提示词文本
     */
    public String buildSystemPrompt(PromptScene scene, QueryIntent intent, List<String> intentTpls,
                                    String kbContext, String mcpContext) {
        // 1. 闲聊用人设模板。追问若已召回证据，走场景模板（KB/MCP），避免 EMPTY 人设盖掉检索约束。
        if (intent == QueryIntent.CHITCHAT) {
            return templateLoader.render("answer-intent-chitchat.st", Map.of());
        }
        if (intent == QueryIntent.FOLLOW_UP && (scene == PromptScene.EMPTY)) {
            return templateLoader.render("answer-intent-follow-up.st", Map.of());
        }

        // 2. 意图级节点覆盖：单意图且配了 promptTemplate → 完全覆盖
        if (intentTpls != null && intentTpls.size() == 1) {
            String override = intentTpls.get(0);
            if (override != null && !override.isBlank()) {
                return override;
            }
        }

        // 3. 场景默认模板
        String path = defaultTemplatePath(scene);
        if (path == null || path.isEmpty()) {
            path = "answer-chat-system.st";
        }
        return templateLoader.render(path, Map.of());
    }

    /** 向后兼容：无意图时走场景路由（等价于 intent=null，意图专用模板不生效） */
    public String buildSystemPrompt(PromptScene scene, List<String> intentTpls,
                                    String kbContext, String mcpContext) {
        return buildSystemPrompt(scene, null, intentTpls, kbContext, mcpContext);
    }

    /** 场景 → 默认模板路径 */
    private String defaultTemplatePath(PromptScene scene) {
        return switch (scene) {
            case KB_ONLY -> "answer-chat-kb.st";
            case MCP_ONLY -> "answer-chat-mcp.st";
            case MIXED -> "answer-chat-mcp-kb-mixed.st";
            case EMPTY -> "answer-chat-system.st";
        };
    }

    /**
     * 渲染 KB 证据段（<documents> 容器），用 context-format.st 的 documents section。
     *
     * @param documentsEvidence 已渲染的逐条文档证据（含 <question>/<content>）
     */
    public String renderKbEvidence(String documentsEvidence) {
        if (documentsEvidence == null || documentsEvidence.isBlank()) return "";
        return templateLoader.renderSection("context-format.st", "documents",
                Map.of("documents", documentsEvidence));
    }

    /** 渲染会话摘要包装段（<conversation-summary>） */
    public String renderSummaryWrapper(String summary) {
        if (summary == null || summary.isBlank()) return "";
        return templateLoader.renderSection("context-format.st", "summary-wrapper",
                Map.of("summary", summary));
    }
}
