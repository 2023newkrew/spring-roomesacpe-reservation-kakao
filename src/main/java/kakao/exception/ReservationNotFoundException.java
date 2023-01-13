package kakao.exception;

import kakao.exception.CustomRuntimeException;

import static kakao.exception.ErrorCode.RESERVATION_NOT_FOUND;

public class ReservationNotFoundException extends CustomRuntimeException {

    public ReservationNotFoundException() {
        super(RESERVATION_NOT_FOUND);
    }
}