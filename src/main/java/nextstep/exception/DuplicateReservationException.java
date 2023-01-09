package nextstep.exception;

public class DuplicateReservationException extends RuntimeException {

    public DuplicateReservationException() {
        super("같은 날짜와 시간에 해당하는 예약이 존재합니다.");
    }

}
