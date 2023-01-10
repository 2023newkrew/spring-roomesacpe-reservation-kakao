package web.exception;

public class NoSuchReservationException extends RuntimeException {

    private static final String message = "해당 예약이 존재하지 않습니다.";

    public NoSuchReservationException() {
        super(NoSuchReservationException.message);
    }
}
