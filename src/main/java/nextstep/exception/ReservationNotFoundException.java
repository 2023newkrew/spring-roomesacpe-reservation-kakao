package nextstep.exception;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException() {
        this("예약을 찾을 수 없습니다.");
    }
    public ReservationNotFoundException(String message) {
        super(message);
    }
}
