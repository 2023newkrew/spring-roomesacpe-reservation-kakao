package nextstep.main.java.nextstep.global.exception.exception;

import nextstep.main.java.nextstep.global.exception.error.ErrorCode;

public class AlreadyReservedThemeException extends ApiException{
    public AlreadyReservedThemeException() {
        super();
    }

    public AlreadyReservedThemeException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getError() {
        return ErrorCode.ALREADY_RESERVED_THEME_ERROR;
    }
}
