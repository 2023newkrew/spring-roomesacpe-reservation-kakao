package kakao.error.exception;

import kakao.error.ErrorCode;

public class DuplicatedThemeException extends CustomRuntimeException {
    private static final ErrorCode errorCode = ErrorCode.DUPLICATE_THEME;

    public DuplicatedThemeException() {
        super(errorCode, null);
    }

    @Override
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}