package kakao.error.exception;

import kakao.error.CustomRuntimeException;

import static kakao.error.ErrorCode.RESERVATION_NOT_FOUND;

public class ReservationNotFoundException extends CustomRuntimeException {

    public ReservationNotFoundException() {
        super(RESERVATION_NOT_FOUND);
    }
}