package nextstep.main.java.nextstep.global.exception.exception;

public class NoSuchReservationException extends RuntimeException {
    public NoSuchReservationException() {
        super();
    }

    public NoSuchReservationException(String message) {
        super(message);
    }

    public NoSuchReservationException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchReservationException(Throwable cause) {
        super(cause);
    }

    protected NoSuchReservationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
