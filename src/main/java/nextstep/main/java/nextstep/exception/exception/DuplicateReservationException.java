package nextstep.main.java.nextstep.exception.exception;

public class DuplicateReservationException extends RuntimeException {
    public DuplicateReservationException() {
        super();
    }

    public DuplicateReservationException(String message) {
        super(message);
    }
}
