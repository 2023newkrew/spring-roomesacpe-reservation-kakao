package roomescape.theme.exception;

import roomescape.common.exception.CustomException;
import roomescape.common.exception.ErrorCode;

public class ThemeException extends CustomException {

    public ThemeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
