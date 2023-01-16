package nextstep.exception;

public class DeleteReservationFailureException extends RuntimeException {
    public DeleteReservationFailureException() {
        super("예약 삭제 실패");
    }
}
