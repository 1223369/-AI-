package sparkx.sparkshop.knowledge.pipeline;

/**
 * 管线阶段接口
 * 每个阶段决定是否处理、是否继续往下传递。
 */
public interface PipelineStage {

    /** 该阶段的逻辑标识，用于日志/追踪 */
    String name();

    /** 是否应执行本阶段（条件裁剪） */
    default boolean shouldRun(PipelineContext ctx) {
        return true;
    }

    /**
     * 执行阶段逻辑。
     * @return StageResult 决定后续走向
     */
    StageResult execute(PipelineContext ctx) throws Exception;

    /** 阶段执行结果 */
    enum StageResult {
        CONTINUE,        // 正常继续下一阶段
        FALLBACK,        // 触发兜底（如召回为空）
        COMPLETE         // 直接完成（如缓存命中/闲聊不检索）
    }
}
