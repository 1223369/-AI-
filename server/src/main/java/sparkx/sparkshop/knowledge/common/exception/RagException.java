package sparkx.sparkshop.knowledge.common.exception;

/**
 * RAG 系统业务异常基类。
 */
public class RagException extends RuntimeException {

    private final int code;

    public RagException(String message) {
        this(500, message);
    }

    public RagException(int code, String message) {
        super(message);
        this.code = code;
    }

    public RagException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public int getCode() { return code; }
}
