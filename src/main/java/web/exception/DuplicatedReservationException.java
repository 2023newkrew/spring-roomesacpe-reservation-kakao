package web.exception;

public class DuplicatedReservationException extends RuntimeException {

    public DuplicatedReservationException() {
        super("해당 시간에 이미 예약이 있습니다.");
    }
}
