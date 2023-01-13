package nextstep.exception;

public class DuplicateReservationException extends RuntimeException {
    public DuplicateReservationException() {
        this("같은 시간에 이미 예약이 있습니다.");
    }
    public DuplicateReservationException(String message) {
        super(message);
    }
}
