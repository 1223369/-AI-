package sparkx.sparkshop.knowledge.pipeline.stages;

import sparkx.sparkshop.knowledge.intent.GuidanceDecision;
import sparkx.sparkshop.knowledge.intent.IntentGuidanceService;
import sparkx.sparkshop.knowledge.intent.QueryIntent;
import sparkx.sparkshop.knowledge.pipeline.PipelineContext;
import sparkx.sparkshop.knowledge.pipeline.PipelineStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 歧义引导阶段（文档 5.1.3 短路①）—— @Order(40)，置信度不足时先反问澄清。
 *
 * 仅在"候选意图≥2"时触发（IntentGuidanceService 内部判定）。
 * ★ 短路：歧义时把澄清选项推给前端并结束整条链路（SHORTCIRCUIT）。
 *
 * 需改造 RagPipeline 支持 SHORTCIRCUIT 结果（现有只支持 COMPLETE/FALLBACK）。
 */
@Component
@Order(40)
public class GuidanceStage implements PipelineStage {

    private static final Logger log = LoggerFactory.getLogger(GuidanceStage.class);

    private final IntentGuidanceService guidanceService;

    public GuidanceStage(IntentGuidanceService guidanceService) {
        this.guidanceService = guidanceService;
    }

    @Override
    public String name() { return "guidance"; }

    @Override
    public boolean shouldRun(PipelineContext ctx) {
        if (ctx.getSubIntents() == null || ctx.getSubIntents().size() < 2) return false;
        if (ctx.getIntent() == QueryIntent.FOLLOW_UP) return false;
        return true;
    }

    @Override
    public StageResult execute(PipelineContext ctx) {
        GuidanceDecision decision = guidanceService.detectAmbiguity(
                ctx.getOriginalQuery(), ctx.getSubIntents());
        ctx.setGuidance(decision);

        if (decision.isPrompt()) {
            // ★ 短路①：歧义时先反问澄清，把选项推给前端并结束整条链路
            if (ctx.getTokenConsumer() != null) {
                ctx.getTokenConsumer().accept(decision.prompt());
            }
            ctx.setAnswer(decision.prompt());
            log.info("[Guidance] 歧义引导触发，短路返回澄清选项");
            return StageResult.COMPLETE;   // 用现有 COMPLETE 结束链路（等价短路）
        }
        return StageResult.CONTINUE;
    }
}
