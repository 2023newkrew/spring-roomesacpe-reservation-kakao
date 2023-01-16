package kakao.error.exception;

import kakao.error.ErrorCode;

public class IllegalCreateReservationRequestException extends CustomRuntimeException {
    private final ErrorCode errorCode;

    public IllegalCreateReservationRequestException(ErrorCode errorCode) {
        super(errorCode, null);
        this.errorCode = errorCode;
    }

    @Override
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
