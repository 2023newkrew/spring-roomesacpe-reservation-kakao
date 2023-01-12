package kakao.error.exception;

import kakao.error.ErrorCode;

public class UsingThemeException extends CustomRuntimeException {
    private final ErrorCode errorCode = ErrorCode.THEME_CONFLICT;

    public UsingThemeException() {
        super(ErrorCode.THEME_CONFLICT, null);
    }

    @Override
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
