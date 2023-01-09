package kakao.error.exception;

import kakao.error.ErrorCode;

public class DuplicatedReservationException extends RuntimeException {
    private final ErrorCode errorCode;

    public DuplicatedReservationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
