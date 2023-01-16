package roomescape.common.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

    protected final HttpStatus status;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = errorCode.getStatus();
    }

    public HttpStatus getStatus() {
        return status;
    }
}
