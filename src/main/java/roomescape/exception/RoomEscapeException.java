package roomescape.exception;

public class RoomEscapeException extends RuntimeException {
    public RoomEscapeException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
