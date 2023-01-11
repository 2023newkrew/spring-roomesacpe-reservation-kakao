package nextstep.main.java.nextstep.global.exception.exception;

import nextstep.main.java.nextstep.global.exception.error.ErrorCode;

public class DuplicateReservationException extends ApiException {
    public DuplicateReservationException() {
        super();
    }

    public DuplicateReservationException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getError() {
        return ErrorCode.DUPLICATE_RESERVATION_ERROR;
    }

}
