package sparkx.sparkshop.knowledge.pipeline;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Consumer;

/**
 * RAG 管线引擎
 *
 * 按顺序执行阶段链，支持：
 *  - 条件裁剪（shouldRun）
 *  - 兜底分支（FALLBACK → 后续 FallbackStage 自然接管）
 *  - 提前完成（COMPLETE，如缓存命中）
 *  - 每阶段计时监控
 *
 * 阶段通过 Spring 自动注入，按 @Order 排序。
 * 新增阶段只需加 @Component 并实现接口，开闭原则。
 */
@Component
public class RagPipeline {

    private static final Logger log = LoggerFactory.getLogger(RagPipeline.class);

    private final List<PipelineStage> stages;
    private final MeterRegistry meterRegistry;

    public RagPipeline(List<PipelineStage> stages, MeterRegistry meterRegistry) {
        this.stages = new ArrayList<>(stages);
        // 按 @Order 注解排序
        AnnotationAwareOrderComparator.sort(this.stages);
        this.meterRegistry = meterRegistry;
        log.info("[Pipeline] 已装配 {} 个阶段: {}", this.stages.size(),
                this.stages.stream().map(PipelineStage::name).toList());
    }

    /** 会调 LLM 的阶段名（用于汇总日志统计 LLM 调用次数） */
    private static final Set<String> LLM_STAGES = Set.of(
            "rewrite-split", "tree-intent", "vague-clarify", "generate", "fallback");

    private static final Map<String, String> STAGE_LABELS = Map.ofEntries(
            Map.entry("sample-query", "样例命中"),
            Map.entry("rewrite-split", "改写拆分"),
            Map.entry("intent", "意图判定"),
            Map.entry("tree-intent", "意图分类"),
            Map.entry("guidance", "歧义引导"),
            Map.entry("vague-clarify", "模糊澄清"),
            Map.entry("retrieve", "检索召回"),
            Map.entry("rerank", "重排"),
            Map.entry("merge", "合并"),
            Map.entry("fallback", "兜底"),
            Map.entry("generate", "生成回答")
    );

    /**
     * 执行完整管线。
     */
    public void run(PipelineContext ctx) throws Exception {
        long pipelineStart = System.currentTimeMillis();
        log.info("[Pipeline] start session={} query=\"{}\"",
                ctx.getSessionId(), ctx.getOriginalQuery());

        // 各阶段耗时累计（name → ms），用于结尾汇总
        Map<String, Long> stageCosts = new LinkedHashMap<>();
        int llmCallCount = 0;

        for (PipelineStage stage : stages) {
            if (!stage.shouldRun(ctx)) {
                log.debug("[Pipeline] skip stage={} (shouldRun=false)", stage.name());
                emitStageEvent(ctx, Map.of(
                        "type", "stage_skip",
                        "name", stage.name(),
                        "label", stageLabel(stage.name())));
                continue;
            }

            emitStageEvent(ctx, Map.of(
                    "type", "stage",
                    "name", stage.name(),
                    "label", stageLabel(stage.name())));

            long stageStart = System.currentTimeMillis();
            Timer.Sample sample = Timer.start(meterRegistry);
            PipelineStage.StageResult result;
            try {
                result = stage.execute(ctx);
            } catch (Exception e) {
                meterRegistry.counter("rag.pipeline.errors",
                        "stage", stage.name(), "error", e.getClass().getSimpleName()).increment();
                log.error("[Pipeline] stage={} failed", stage.name(), e);
                emitStageEvent(ctx, Map.of(
                        "type", "stage_end",
                        "name", stage.name(),
                        "label", stageLabel(stage.name()),
                        "costMs", System.currentTimeMillis() - stageStart,
                        "result", "ERROR"));
                throw e;
            } finally {
                sample.stop(Timer.builder("rag.pipeline.stage")
                        .tag("stage", stage.name())
                        .register(meterRegistry));
            }

            long cost = System.currentTimeMillis() - stageStart;
            stageCosts.put(stage.name(), cost);
            ctx.getStageTimings().put(stage.name(), cost);   // ★ 同步写入 ctx，供前端时间线展示
            if (LLM_STAGES.contains(stage.name())) {
                llmCallCount++;
            }
            log.info("[Pipeline:diag] stage={} 耗时={}ms{}", stage.name(), cost,
                    LLM_STAGES.contains(stage.name()) ? " [含LLM]" : "");
            log.debug("[Pipeline] stage={} result={}", stage.name(), result);
            emitStageEvent(ctx, Map.of(
                    "type", "stage_end",
                    "name", stage.name(),
                    "label", stageLabel(stage.name()),
                    "costMs", cost,
                    "result", result.name()));

            if (result == PipelineStage.StageResult.FALLBACK) {
                log.info("[Pipeline] fallback triggered at stage={}", stage.name());
                ctx.setAttr("needFallback", true);
            }
            if (result == PipelineStage.StageResult.COMPLETE) {
                log.info("[Pipeline] early complete at stage={}", stage.name());
                break;
            }
        }

        long totalCost = System.currentTimeMillis() - pipelineStart;
        // ★ 写入 ctx，供 SSE complete 事件回传前端时间线展示
        ctx.setTotalCost(totalCost);
        ctx.setLlmCallCount(llmCallCount);
        // ★ 汇总：一条日志看清总耗时 + 各阶段分布 + LLM 调用次数（卡顿排查用）
        log.info("[Pipeline:diag] ===== 汇总 总耗时={}ms LLM调用={}次 阶段分布={} session={} =====",
                totalCost, llmCallCount, stageCosts, ctx.getSessionId());
        log.info("[Pipeline] done session={} answerLen={}",
                ctx.getSessionId(), ctx.getAnswer() == null ? 0 : ctx.getAnswer().length());
    }

    private static String stageLabel(String name) {
        return STAGE_LABELS.getOrDefault(name, name);
    }

    private void emitStageEvent(PipelineContext ctx, Map<String, Object> event) {
        Consumer<Map<String, Object>> consumer = ctx.getStageEventConsumer();
        if (consumer == null) {
            return;
        }
        try {
            consumer.accept(event);
        } catch (Exception e) {
            log.debug("[Pipeline] stage event 推送失败 stage={}: {}", event.get("name"), e.getMessage());
        }
    }
}
