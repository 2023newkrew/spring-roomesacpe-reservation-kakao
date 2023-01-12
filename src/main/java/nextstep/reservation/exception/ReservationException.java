package nextstep.reservation.exception;

public class ReservationException extends RuntimeException {
    public ReservationException(ReservationExceptionCode code) {
        super(code.getMessage());
    }
}
