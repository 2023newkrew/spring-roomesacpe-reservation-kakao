package nextstep.exception;

public class ReservedThemeException extends RuntimeException {
    public ReservedThemeException() {
        this("예약되어 있는 테마는 수정/삭제할 수 없습니다.");
    }
    public ReservedThemeException(String message) {
        super(message);
    }
}
