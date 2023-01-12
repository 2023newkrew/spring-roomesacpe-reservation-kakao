package kakao.error.exception;

import kakao.error.ErrorCode;

public class UsingThemeException extends RuntimeException {
    private final ErrorCode errorCode = ErrorCode.THEME_CONFLICT;

    public UsingThemeException() {
        super(ErrorCode.THEME_CONFLICT.getMessage());
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
