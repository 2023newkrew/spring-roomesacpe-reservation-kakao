package roomescape.theme.exception;

public class NoSuchThemeException extends RuntimeException {

    public NoSuchThemeException() {
        super("해당 테마가 존재하지 않습니다");
    }
}
