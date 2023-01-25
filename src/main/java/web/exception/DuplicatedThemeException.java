package web.exception;

public class DuplicatedThemeException extends RuntimeException {

    private static final String MESSAGE = "해당 이름의 테마가 이미 있습니다.";

    public DuplicatedThemeException() {
        super(DuplicatedThemeException.MESSAGE);
    }
}
