package roomescape.theme.exception;

public class DuplicatedThemeException extends RuntimeException {

    public DuplicatedThemeException() {
        super("이미 존재하는 테마입니다.");
    }
}
