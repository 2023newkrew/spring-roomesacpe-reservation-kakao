package roomescape.exceptions.exception;

public class DuplicatedReservationException extends RuntimeException {
    private static String DUPLICATED_RESERVATION = "이미 존재하는 예약입니다.";
    public DuplicatedReservationException() {
        this(DUPLICATED_RESERVATION);
    }

    public DuplicatedReservationException(String message) {
        super(message);
    }
}
