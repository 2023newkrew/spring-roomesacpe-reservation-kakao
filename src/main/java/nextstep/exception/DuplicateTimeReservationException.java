package nextstep.exception;

public class DuplicateTimeReservationException extends RuntimeException{
    public DuplicateTimeReservationException() {
        super("같은 시간에 이미 예약이 존재");
    }
}
