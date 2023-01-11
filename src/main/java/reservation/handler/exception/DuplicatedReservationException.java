package reservation.handler.exception;

public class DuplicatedReservationException extends RuntimeException {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
