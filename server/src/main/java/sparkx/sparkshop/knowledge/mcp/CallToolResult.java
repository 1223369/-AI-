package sparkx.sparkshop.knowledge.mcp;

/**
 * 工具调用结果（文档 5.12）—— 简化版，与 MCP SDK 的 CallToolResult 解耦。
 *
 * @param content 工具返回内容（文本）
 * @param isError 是否为错误结果
 */
public record CallToolResult(String content, boolean isError) {

    public static CallToolResult ok(String content) { return new CallToolResult(content, false); }

    public static CallToolResult error(String msg) { return new CallToolResult(msg, true); }
}
