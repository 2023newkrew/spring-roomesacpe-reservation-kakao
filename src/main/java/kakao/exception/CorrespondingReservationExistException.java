package kakao.exception;

import static kakao.exception.ErrorCode.CORRESPONDING_RESERVATION_EXIST;

public class CorrespondingReservationExistException extends CustomRuntimeException {

    public CorrespondingReservationExistException() {
        super(CORRESPONDING_RESERVATION_EXIST);
    }
}
