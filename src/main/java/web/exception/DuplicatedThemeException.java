package web.exception;

public class DuplicatedThemeException extends RuntimeException {

    private static final String MESSAGE = "해당 시간에 이미 예약이 있습니다.";

    public DuplicatedThemeException() {
        super(DuplicatedThemeException.MESSAGE);
    }
}
