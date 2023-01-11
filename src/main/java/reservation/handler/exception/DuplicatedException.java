package reservation.handler.exception;

public class DuplicatedException extends RuntimeException {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
