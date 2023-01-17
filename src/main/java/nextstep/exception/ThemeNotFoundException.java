package nextstep.exception;

public class ThemeNotFoundException extends RuntimeException{
    public ThemeNotFoundException() {
        super("해당하는 테마를 찾을 수 없습니다.");
    }
}
