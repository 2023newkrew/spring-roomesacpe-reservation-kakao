package nextstep.exceptions.exception;

import nextstep.exceptions.ErrorCode;
import org.springframework.http.HttpStatus;

public class InvalidInputException extends RuntimeException {
    private final ErrorCode errorCode;

    public InvalidInputException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public HttpStatus getHttpStatus() {
        return errorCode.getHttpStatus();
    }

    public String getMessage() {
        return errorCode.getMessage();
    }
}
