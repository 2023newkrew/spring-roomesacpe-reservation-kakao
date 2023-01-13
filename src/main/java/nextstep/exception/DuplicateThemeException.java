package nextstep.exception;

public class DuplicateThemeException extends RuntimeException {
    public DuplicateThemeException() {
        super("동일한 이름의 테마가 이미 존재합니다.");
    }
}
