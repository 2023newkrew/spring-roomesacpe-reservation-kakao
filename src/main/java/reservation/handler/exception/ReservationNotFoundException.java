package reservation.handler.exception;

public class ReservationNotFoundException extends RuntimeException {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
