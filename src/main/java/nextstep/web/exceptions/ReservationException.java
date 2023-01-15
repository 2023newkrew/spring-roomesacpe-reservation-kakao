package nextstep.web.exceptions;

public class ReservationException extends RuntimeException {
    public ReservationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
