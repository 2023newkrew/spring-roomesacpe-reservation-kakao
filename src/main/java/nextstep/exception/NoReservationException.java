package nextstep.exception;

public class NoReservationException extends RuntimeException {
    public NoReservationException() {
        super("해당 예약이 존재하지 않음");
    }
}
