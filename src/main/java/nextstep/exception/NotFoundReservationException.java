package nextstep.exception;

public class NotFoundReservationException extends RoomEscapeException {
    public NotFoundReservationException() {
        super();
    }

    public NotFoundReservationException(String message) {
        super(message);
    }

    public NotFoundReservationException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundReservationException(Throwable cause) {
        super(cause);
    }
}
