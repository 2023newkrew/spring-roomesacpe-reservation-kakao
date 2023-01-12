package reservation.handler.exception;

public class ObjectNotFoundException extends RuntimeException {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
