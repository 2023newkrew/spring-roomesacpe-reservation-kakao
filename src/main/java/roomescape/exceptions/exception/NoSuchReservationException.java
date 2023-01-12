package roomescape.exceptions.exception;

public class NoSuchReservationException extends RuntimeException {
    public NoSuchReservationException(String message) {
        super(message);
    }
    private static String NO_SUCH_RESERVATION = "존재하지 않는 예약입니다.";

    public NoSuchReservationException() {
        this(NO_SUCH_RESERVATION);
    }
}
