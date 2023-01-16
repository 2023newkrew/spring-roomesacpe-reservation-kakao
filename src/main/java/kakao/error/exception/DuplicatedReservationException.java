package kakao.error.exception;

import kakao.error.ErrorCode;

public class DuplicatedReservationException extends CustomRuntimeException {
    private static final ErrorCode errorCode = ErrorCode.DUPLICATE_RESERVATION;

    public DuplicatedReservationException() {
        super(errorCode, null);
    }

    @Override
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
