package kakao.exception;

import kakao.exception.CustomRuntimeException;

import static kakao.exception.ErrorCode.DUPLICATE_RESERVATION;

public class DuplicatedReservationException extends CustomRuntimeException {

    public DuplicatedReservationException() {
        super(DUPLICATE_RESERVATION);
    }
}
