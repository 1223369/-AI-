package sparkx.sparkshop.knowledge.mcp;

/**
 * MCP 工具注册表接口（文档 5.12.1）。
 */
public interface McpToolRegistry {

    void register(McpToolExecutor executor);

    void unregister(String toolId);

    McpToolExecutor getExecutor(String toolId);

    java.util.List<McpToolExecutor> listAllTools();

    boolean contains(String toolId);

    int size();
}
