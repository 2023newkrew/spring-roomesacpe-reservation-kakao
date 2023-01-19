package nextstep.exception;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException() {
        this("유효하지 않은 요청입니다.");
    }
    public InvalidRequestException(String message) {
        super(message);
    }
}
