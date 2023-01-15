package nextstep.web.exceptions;

public class ThemeException extends RuntimeException {
    public ThemeException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
