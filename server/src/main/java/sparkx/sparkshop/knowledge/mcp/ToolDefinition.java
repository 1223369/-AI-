package sparkx.sparkshop.knowledge.mcp;

import java.util.Map;

/**
 * 工具定义（文档 5.12.1）—— 工具名/描述/参数 schema。
 *
 * 本阶段为自研简化版（避免直接强依赖 mcp-java-sdk 运行时类型），
 * 后续接入真实 MCP Server 时可桥接到 io.modelcontextprotocol 的 McpSchema.Tool。
 */
public record ToolDefinition(
        String name,
        String description,
        /** 参数 schema：参数名 → 参数定义（type/required/description/defaultValue） */
        Map<String, ParamDef> inputSchema
) {
    /** 参数定义 */
    public record ParamDef(String type, boolean required, String description, String defaultValue) { }
}
