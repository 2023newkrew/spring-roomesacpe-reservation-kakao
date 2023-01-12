package nextstep.etc.exception;

public class ReservationConflictException extends BaseException {

    public ReservationConflictException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
