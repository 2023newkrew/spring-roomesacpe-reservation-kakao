package nextstep.main.java.nextstep.global.exception.exception;

import nextstep.main.java.nextstep.global.exception.error.ErrorCode;

public class NoSuchThemeException extends ApiException{
    public NoSuchThemeException() {
        super();
    }

    public NoSuchThemeException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getError() {
        return null;
    }
}
