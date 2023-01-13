package roomescape.exceptions.exception;

public class ReservationNotFoundException extends RuntimeException {
    private static String RESERVATION_NOT_FOUND = "존재하지 않는 예약입니다.";

    public ReservationNotFoundException() {
        this(RESERVATION_NOT_FOUND);
    }

    public ReservationNotFoundException(String message) {
        super(message);
    }
}
