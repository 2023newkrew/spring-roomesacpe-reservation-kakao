package roomescape.exceptions.exception;

public class ThemeNotFoundException extends RuntimeException {
    private final static String THEME_NOT_FOUND = "존재하지 않는 테마입니다.";

    public ThemeNotFoundException() {
        this(THEME_NOT_FOUND);
    }

    public ThemeNotFoundException(String message) {
        super(message);
    }
}
