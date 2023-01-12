package web.exception;

public class ReservationDuplicateException extends RuntimeException {

    public ReservationDuplicateException() {
        super("요청하신 시간대에 이미 예약이 있습니다.");
    }
}
