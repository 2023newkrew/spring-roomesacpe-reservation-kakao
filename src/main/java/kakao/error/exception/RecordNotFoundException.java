package kakao.error.exception;

import kakao.error.ErrorCode;

public class RecordNotFoundException extends CustomException {
    private final ErrorCode errorCode;

    public RecordNotFoundException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
