package nextstep.exception;

public class ReservationDuplicateException extends RoomEscapeException {
    public ReservationDuplicateException() {
        super("같은 날짜와 시간에 이미 예약이 존재합니다.");
    }
}
