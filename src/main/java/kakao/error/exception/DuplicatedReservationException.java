package kakao.error.exception;

import kakao.error.ErrorCode;

public class DuplicatedReservationException extends CustomRuntimeException {
    private final ErrorCode errorCode = ErrorCode.DUPLICATE_RESERVATION;

    public DuplicatedReservationException() {
        super(ErrorCode.DUPLICATE_RESERVATION, null);
    }

    @Override
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
