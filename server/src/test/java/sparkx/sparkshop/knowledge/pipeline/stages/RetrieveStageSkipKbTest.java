package sparkx.sparkshop.knowledge.pipeline.stages;

import org.junit.jupiter.api.Test;
import sparkx.sparkshop.knowledge.config.RagProperties;
import sparkx.sparkshop.knowledge.intent.IntentNode;
import sparkx.sparkshop.knowledge.intent.NodeScore;
import sparkx.sparkshop.knowledge.retrieval.ConditionalRetrievalChannel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RetrieveStageSkipKbTest {

    @Test
    void mcpOnlySkipsKb() {
        assertTrue(RetrieveStage.skipKbChannels(List.of(mcp("stock", 0.92))));
    }

    @Test
    void emptyOrKbOnlyStillRetrieves() {
        assertFalse(RetrieveStage.skipKbChannels(null));
        assertFalse(RetrieveStage.skipKbChannels(List.of()));
        assertFalse(RetrieveStage.skipKbChannels(List.of(kb("doc", 0.80))));
    }

    @Test
    void strongMcpOverKbSkips() {
        assertTrue(RetrieveStage.skipKbChannels(List.of(mcp("stock", 0.92), kb("doc", 0.40))));
    }

    @Test
    void mixedCloseScoresStillRetrieves() {
        assertFalse(RetrieveStage.skipKbChannels(List.of(mcp("stock", 0.70), kb("doc", 0.68))));
        assertFalse(RetrieveStage.skipKbChannels(List.of(mcp("stock", 0.75), kb("doc", 0.70))));
    }

    @Test
    void defaultKbDoesNotRunGraph() {
        IntentNode n = new IntentNode();
        n.setId("sys_default_retrieval");
        n.setKind(IntentNode.IntentKind.KB);
        assertFalse(sparkx.sparkshop.knowledge.graph.KnowledgeGraphChannel.shouldRunGraph(
                List.of(new NodeScore(n, 0.50))));
    }

    @Test
    void strongUserKbRunsGraph() {
        assertTrue(sparkx.sparkshop.knowledge.graph.KnowledgeGraphChannel.shouldRunGraph(
                List.of(kb("oa-rules", 0.80))));
    }

    @Test
    void channelTimeoutsAreSplit() {
        RagProperties.Retrieval r = new RagProperties.Retrieval();
        r.setKeywordTimeoutMs(1000);
        r.setVectorTimeoutMs(20000);
        r.setChannelTimeoutMs(5000);
        assertEquals(20500, stub(ConditionalRetrievalChannel.ChannelType.HYBRID_GLOBAL).timeoutMs(r));
        assertEquals(20500, stub(ConditionalRetrievalChannel.ChannelType.INTENT_DIRECTED).timeoutMs(r));
        assertEquals(5000, stub(ConditionalRetrievalChannel.ChannelType.KNOWLEDGE_GRAPH).timeoutMs(r));
        assertEquals(1000, stub(ConditionalRetrievalChannel.ChannelType.KEYWORD).timeoutMs(r));
    }

    private static ConditionalRetrievalChannel stub(ConditionalRetrievalChannel.ChannelType type) {
        return new ConditionalRetrievalChannel() {
            @Override public String getName() { return type.name(); }
            @Override public int getPriority() { return 1; }
            @Override public boolean isEnabled(sparkx.sparkshop.knowledge.retrieval.RetrievalContext ctx) { return true; }
            @Override public java.util.List<dev.langchain4j.rag.content.Content> retrieve(
                    dev.langchain4j.rag.query.Query query,
                    sparkx.sparkshop.knowledge.retrieval.RetrievalContext ctx) {
                return List.of();
            }
            @Override public ChannelType getType() { return type; }
        };
    }

    private static NodeScore mcp(String id, double score) {
        IntentNode n = new IntentNode();
        n.setId(id);
        n.setName(id);
        n.setKind(IntentNode.IntentKind.MCP);
        n.setMcpToolId("svc1__" + id);
        return new NodeScore(n, score);
    }

    private static NodeScore kb(String id, double score) {
        IntentNode n = new IntentNode();
        n.setId(id);
        n.setName(id);
        n.setKind(IntentNode.IntentKind.KB);
        n.setCollectionName("kb-1");
        return new NodeScore(n, score);
    }
}
