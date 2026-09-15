package sparkx.sparkshop.knowledge.infra.chat;

/**
 * 模型客户端错误类型分类（文档 5.10）。
 * 由 HTTP 状态码映射，供 {@link ModelClientException} 携带，便于熔断器/日志区分。
 */
public enum ModelClientErrorType {
    /** 未授权（401/403，通常是 API Key 错误） */
    UNAUTHORIZED,
    /** 限流（429） */
    RATE_LIMITED,
    /** 服务端错误（5xx） */
    SERVER_ERROR,
    /** 其他客户端错误（4xx） */
    CLIENT_ERROR,
    /** 网络错误（连接超时/读超时/断连） */
    NETWORK_ERROR,
    /** 响应格式异常（流结束无完成标记、JSON 解析失败） */
    INVALID_RESPONSE,
    /** 供应商侧错误（无法归类） */
    PROVIDER_ERROR;

    /** HTTP 状态码 → 错误类型 */
    public static ModelClientErrorType fromHttpStatus(int status) {
        if (status == 401 || status == 403) return UNAUTHORIZED;
        if (status == 429) return RATE_LIMITED;
        if (status >= 500) return SERVER_ERROR;
        return CLIENT_ERROR;
    }
}
