package kakao.error.exception;

import kakao.error.ErrorCode;

public class DuplicatedReservationException extends RuntimeException {
    private final ErrorCode errorCode = ErrorCode.DUPLICATE_RESERVATION;

    public DuplicatedReservationException() {
        super(ErrorCode.DUPLICATE_RESERVATION.getMessage());
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
