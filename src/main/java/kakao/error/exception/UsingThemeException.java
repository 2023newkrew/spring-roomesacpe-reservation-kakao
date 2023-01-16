package kakao.error.exception;

import kakao.error.ErrorCode;

public class UsingThemeException extends CustomRuntimeException {
    private static final ErrorCode errorCode = ErrorCode.THEME_CONFLICT;

    public UsingThemeException() {
        super(errorCode, null);
    }

    @Override
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
