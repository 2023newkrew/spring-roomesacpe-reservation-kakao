package web.exception;

public class NoSuchReservationException extends RuntimeException {

    private static final String MESSAGE = "해당 예약이 존재하지 않습니다.";

    public NoSuchReservationException() {
        super(NoSuchReservationException.MESSAGE);
    }
}
