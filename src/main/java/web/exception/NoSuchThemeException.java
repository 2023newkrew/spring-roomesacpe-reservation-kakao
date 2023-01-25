package web.exception;

public class NoSuchThemeException extends RuntimeException {

    private static final String MESSAGE = "해당 테마가 존재하지 않습니다.";

    public NoSuchThemeException() {
        super(NoSuchThemeException.MESSAGE);
    }
}
