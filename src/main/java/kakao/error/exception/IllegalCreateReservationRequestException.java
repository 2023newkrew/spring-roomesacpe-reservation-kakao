package kakao.error.exception;

import kakao.error.ErrorCode;

public class IllegalCreateReservationRequestException extends IllegalArgumentException {
    private final ErrorCode errorCode;

    public IllegalCreateReservationRequestException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
