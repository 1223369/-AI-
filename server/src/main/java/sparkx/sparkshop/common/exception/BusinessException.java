package sparkx.sparkshop.common.exception;

import lombok.Getter;

/**
 * 业务异常，被全局异常处理器捕获后返回给前端
 */
@Getter
public class BusinessException extends RuntimeException {

    private final Integer code;

    /**
     * 构造业务异常，code 默认 -1
     *
     * @param message 异常提示信息
     */
    public BusinessException(String message) {
        super(message);
        this.code = -1;
    }

    /**
     * 构造业务异常，自定义 code
     *
     * @param code    状态码
     * @param message 异常提示信息
     */
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
