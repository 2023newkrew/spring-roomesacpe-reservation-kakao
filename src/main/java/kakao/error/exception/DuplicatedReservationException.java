package kakao.error.exception;

import kakao.error.ErrorCode;

public class DuplicatedReservationException extends CustomException {
    private final ErrorCode errorCode;

    public DuplicatedReservationException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
