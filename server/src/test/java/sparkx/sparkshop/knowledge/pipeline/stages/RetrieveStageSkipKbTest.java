package sparkx.sparkshop.knowledge.pipeline.stages;

import org.junit.jupiter.api.Test;
import sparkx.sparkshop.knowledge.intent.IntentNode;
import sparkx.sparkshop.knowledge.intent.NodeScore;

import java.util.List;

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
