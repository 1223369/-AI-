package sparkx.sparkshop.workflow.engine;

/**
 * 流式节点结束回调（LLM 节点流完成后回写 token 用量）。
 */
@FunctionalInterface
public interface SendEndCallback {

    /** 接收流结束后的结果 JSON */
    void accept(String result);
}
