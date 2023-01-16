package reservation.handler.exception;

public class DuplicatedObjectException extends RuntimeException {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
