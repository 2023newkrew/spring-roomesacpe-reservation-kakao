package nextstep.exceptions.exception;

import nextstep.exceptions.ErrorCode;
import org.springframework.http.HttpStatus;

public class InvalidRequestException extends RuntimeException {
    private final ErrorCode errorCode;

    public InvalidRequestException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public HttpStatus getHttpStatus() {
        return errorCode.getHttpStatus();
    }

    public String getMessage() {
        return errorCode.getMessage();
    }
}
