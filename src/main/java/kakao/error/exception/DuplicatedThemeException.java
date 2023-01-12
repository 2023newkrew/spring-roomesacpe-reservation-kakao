package kakao.error.exception;

import kakao.error.ErrorCode;

public class DuplicatedThemeException extends CustomRuntimeException {
    private final ErrorCode errorCode = ErrorCode.DUPLICATE_THEME;

    public DuplicatedThemeException() {
        super(ErrorCode.DUPLICATE_THEME, null);
    }
    
    @Override
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}