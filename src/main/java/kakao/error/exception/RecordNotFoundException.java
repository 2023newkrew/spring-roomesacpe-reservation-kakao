package kakao.error.exception;

import kakao.error.ErrorCode;

public class RecordNotFoundException extends CustomRuntimeException {
    private final ErrorCode errorCode;

    public RecordNotFoundException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
        this.errorCode = errorCode;
    }

    @Override
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
