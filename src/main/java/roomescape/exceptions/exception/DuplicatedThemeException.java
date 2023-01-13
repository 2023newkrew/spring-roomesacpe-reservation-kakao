package roomescape.exceptions.exception;

public class DuplicatedThemeException extends RuntimeException {
    private final static String DUPLICATED_THEME = "이미 존재하는 테마명입니다.";

    public DuplicatedThemeException() {
        this(DUPLICATED_THEME);
    }

    public DuplicatedThemeException(String message) {
        super(message);
    }
}
