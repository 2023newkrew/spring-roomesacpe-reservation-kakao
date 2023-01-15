package roomescape.theme.exception;

public class AlreadyReservedThemeException extends RuntimeException {

    public AlreadyReservedThemeException() {
        super("이미 예약되어 있는 테마는 변경할 수 없습니다.");
    }
}
