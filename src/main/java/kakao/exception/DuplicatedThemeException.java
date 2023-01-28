package kakao.exception;

import static kakao.exception.ErrorCode.DUPLICATE_THEME;
public class DuplicatedThemeException extends CustomRuntimeException {
    public DuplicatedThemeException() {
        super(DUPLICATE_THEME);
    }
}
