package roomescape.reservation.exception;

import roomescape.common.exception.CustomException;
import roomescape.common.exception.ErrorCode;

public class ReservationException extends CustomException {

    public ReservationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
