package nextstep.reservation.exception;

public class RoomEscapeException extends RuntimeException {
    public RoomEscapeException(RoomEscapeExceptionCode code) {
        super(code.getMessage());
    }
}
