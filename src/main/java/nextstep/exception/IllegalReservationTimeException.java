package nextstep.exception;

public class IllegalReservationTimeException extends RuntimeException {
    public IllegalReservationTimeException() {
        super("예약 시간은 30분 단위");
    }
}
