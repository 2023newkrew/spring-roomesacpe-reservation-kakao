package kakao.error.exception;

import kakao.error.ErrorCode;

public abstract class CustomRuntimeException extends RuntimeException {
    public CustomRuntimeException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
    }

    public abstract ErrorCode getErrorCode();
}
