package nextstep.main.java.nextstep.exception;

public class NoSuchReservationException extends RuntimeException{
    public NoSuchReservationException() {
        super();
    }

    public NoSuchReservationException(String message) {
        super(message);
    }
}
