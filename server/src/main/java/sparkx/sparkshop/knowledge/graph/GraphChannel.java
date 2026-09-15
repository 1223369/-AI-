package sparkx.sparkshop.knowledge.graph;

import sparkx.sparkshop.knowledge.retrieval.ConditionalRetrievalChannel;

/**
 * 知识图谱检索通道标记接口。
 *
 * <p>继承自 {@link ConditionalRetrievalChannel}（被 {@code RetrieveStage} 自动收集），
 * 额外作为 {@code @ConditionalOnMissingBean(GraphChannel.class)} 的判定键——
 * 这样 {@code GraphNoopConfig} 能在"没有真实 KG 通道"时装配兜底，
 * 不受其他类型 Channel（IntentDirected/VectorKeywordHybrid）干扰。
 *
 * <p>实现类：
 * <ul>
 *   <li>{@link NoopGraphChannel}：KG 关闭时兜底，{@code isEnabled} 恒 false</li>
 *   <li>{@code KnowledgeGraphChannel}：KG 开启时的真实检索通道（阶段 2 落地）</li>
 * </ul>
 */
public interface GraphChannel extends ConditionalRetrievalChannel {
}
