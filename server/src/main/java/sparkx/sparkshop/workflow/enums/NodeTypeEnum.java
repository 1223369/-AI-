package sparkx.sparkshop.workflow.enums;

/**
 * 编排节点类型。code 为 X6 的 shape 名，作为节点分发 key。
 */
public enum NodeTypeEnum {

    AGENT("agent-node", "智能体节点"),
    ANSWER("answer-node", "回复节点"),
    DATASET("dataset-node", "知识库节点"),
    GRAPH("graph-node", "知识图谱节点"),
    LLM("llm-node", "大模型节点"),
    PURPOSE("purpose-node", "意图分类节点"),
    SWITCH("switch-node", "分支节点");

    private final String code;
    private final String msg;

    NodeTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
