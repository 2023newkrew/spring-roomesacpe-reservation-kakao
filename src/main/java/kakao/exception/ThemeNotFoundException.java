package kakao.exception;

import static kakao.exception.ErrorCode.THEME_NOT_FOUND;

public class ThemeNotFoundException extends CustomRuntimeException {

    public ThemeNotFoundException() {
        super(THEME_NOT_FOUND);
    }
}
