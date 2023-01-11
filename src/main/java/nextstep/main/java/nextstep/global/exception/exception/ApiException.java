package nextstep.main.java.nextstep.global.exception.exception;

import nextstep.main.java.nextstep.global.exception.error.ErrorCode;

public abstract class ApiException extends RuntimeException {
    private Error error;
    public ApiException() {
    }

    public ApiException(String message) {
        super(message);
    }

    public abstract ErrorCode getError();
}
