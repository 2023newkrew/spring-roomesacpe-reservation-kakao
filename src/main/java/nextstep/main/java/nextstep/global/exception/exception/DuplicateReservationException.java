package nextstep.main.java.nextstep.global.exception.exception;

public class DuplicateReservationException extends RuntimeException {
    public DuplicateReservationException() {
        super();
    }

    public DuplicateReservationException(String message) {
        super(message);
    }

    public DuplicateReservationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateReservationException(Throwable cause) {
        super(cause);
    }

    protected DuplicateReservationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
