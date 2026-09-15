package sparkx.sparkshop.knowledge.pipeline.stages;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import sparkx.sparkshop.knowledge.entity.SampleQuery;
import sparkx.sparkshop.knowledge.pipeline.AgentOverrides;
import sparkx.sparkshop.knowledge.pipeline.PipelineContext;
import sparkx.sparkshop.knowledge.pipeline.PipelineStage;
import sparkx.sparkshop.knowledge.service.SampleQueryService;

import java.util.Optional;

/**
 * 样例查询优先匹配阶段 —— @Order(5)，整条 RAG 链路的最前置。
 *
 * <p>用用户原始 query 直接走向量匹配样例库（{@code sample_query} 表），命中阈值则把样例答案
 * 作为最终回答返回，<b>跳过改写/意图/检索/重排/兜底/生成全部阶段，LLM 不参与</b>。
 *
 * <p>★ 仅当智能体配置 {@code sampleQueryEnabled=1} 时执行（shouldRun 判定），
 * 与知识库模式（all/selected/none）无关——样例查询是独立于 KB 检索的「快速命中即返回」能力。
 *
 * <p>★ 阈值优先级：智能体独立 {@code sampleQueryThreshold} 非空用它，否则回退全局
 * {@code sample_query_config.similarity_threshold}（默认 0.85）。
 *
 * <p>★ 短路约定（与 {@code GuidanceStage}/{@code VagueQueryClarifyStage} 一致的三步走）：
 * tokenConsumer 推答案 → setAnswer → 返回 COMPLETE。命中 GenerateStage.shouldRun
 * （{@code ctx.getAnswer()==null}）自动跳过生成。匹配失败/未命中返回 CONTINUE，主链路照常跑。
 */
@Component
@Order(5)
public class SampleQueryStage implements PipelineStage {

    private static final Logger log = LoggerFactory.getLogger(SampleQueryStage.class);

    @Resource
    private SampleQueryService sampleQueryService;

    @Override
    public String name() {
        return "sample-query";
    }

    @Override
    public boolean shouldRun(PipelineContext ctx) {
        AgentOverrides ov = ctx.getAgentOverrides();
        if (ov == null
                || !Boolean.TRUE.equals(ov.getSampleQueryEnabled())
                || StrUtil.isBlank(ctx.getOriginalQuery())) {
            return false;
        }
        // 库空仍打 embedding 会白烧十几秒；没有可匹配样例直接跳过
        if (!sampleQueryService.hasIndexedSamples()) {
            log.info("[SampleQuery] 样例库为空，跳过匹配");
            return false;
        }
        return true;
    }

    @Override
    public StageResult execute(PipelineContext ctx) {
        Double thresholdOverride = ctx.getAgentOverrides().getSampleQueryThreshold();
        Optional<SampleQuery> hit = sampleQueryService.match(ctx.getOriginalQuery(), thresholdOverride);
        if (hit.isEmpty()) {
            return StageResult.CONTINUE;
        }
        String answer = hit.get().getAnswer();
        // 样例没配答案不短路，交回主链路
        if (StrUtil.isBlank(answer)) {
            return StageResult.CONTINUE;
        }
        // ★ 短路三步走：推 SSE → 写 answer → COMPLETE 结束链路
        if (ctx.getTokenConsumer() != null) {
            ctx.getTokenConsumer().accept(answer);
        }
        ctx.setAnswer(answer);
        log.info("[SampleQuery] 命中样例 id={}，短路返回样例答案", hit.get().getId());
        return StageResult.COMPLETE;
    }
}
