package kakao.error.exception;

import kakao.error.ErrorCode;

public class DuplicatedThemeException extends RuntimeException {
    private final ErrorCode errorCode = ErrorCode.DUPLICATE_THEME;

    public DuplicatedThemeException() {
        super(ErrorCode.DUPLICATE_THEME.getMessage());
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}