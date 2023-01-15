package nextstep.exception;

public class ThemeNotFoundException extends RuntimeException {
    public ThemeNotFoundException() {
        this("테마를 찾을 수 없습니다.");
    }
    public ThemeNotFoundException(String message) {
        super(message);
    }
}
