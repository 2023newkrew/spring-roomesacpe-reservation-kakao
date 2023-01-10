package web.exception;

public class DuplicatedReservationException extends RuntimeException {

    private static final String message = "해당 시간에 이미 예약이 있습니다.";

    public DuplicatedReservationException() {
        super(DuplicatedReservationException.message);
    }
}
