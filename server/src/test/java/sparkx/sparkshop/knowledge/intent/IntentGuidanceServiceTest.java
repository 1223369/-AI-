package sparkx.sparkshop.knowledge.intent;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

class IntentGuidanceServiceTest {

    @Test
    void sameMcpServerDoesNotClarify() {
        IntentGuidanceService svc = new IntentGuidanceService(null);
        IntentNode a = mcp("device-info", "svc2__get_device_info");
        IntentNode b = mcp("device-alerts", "svc2__get_alerts");
        GuidanceDecision d = svc.detectAmbiguity(
                "查询它昨天的状态并分析相关数据",
                List.of(new NodeScore(a, 0.82), new NodeScore(b, 0.80)));
        assertFalse(d.isPrompt());
    }

    private static IntentNode mcp(String id, String toolId) {
        IntentNode n = new IntentNode();
        n.setId(id);
        n.setName(id);
        n.setKind(IntentNode.IntentKind.MCP);
        n.setMcpToolId(toolId);
        return n;
    }
}
