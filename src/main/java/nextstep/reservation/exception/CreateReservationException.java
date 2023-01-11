package nextstep.reservation.exception;

public class CreateReservationException extends RuntimeException {
    public CreateReservationException(ReservationExceptionCode code) {
        super(code.getMessage());
    }
}
