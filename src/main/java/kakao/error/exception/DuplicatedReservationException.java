package kakao.error.exception;

import kakao.error.CustomRuntimeException;

import static kakao.error.ErrorCode.DUPLICATE_RESERVATION;

public class DuplicatedReservationException extends CustomRuntimeException {

    public DuplicatedReservationException() {
        super(DUPLICATE_RESERVATION);
    }
}
