package kakao.error.exception;

import kakao.error.ErrorCode;

public class RecordNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public RecordNotFoundException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
