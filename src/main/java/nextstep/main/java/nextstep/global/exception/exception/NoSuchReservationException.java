package nextstep.main.java.nextstep.global.exception.exception;

import nextstep.main.java.nextstep.global.exception.error.ErrorCode;

public class NoSuchReservationException extends ApiException {
    public NoSuchReservationException() {
        super();
    }

    public NoSuchReservationException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getError() {
        return ErrorCode.NO_SUCH_RESERVATION_ERROR;
    }

}
