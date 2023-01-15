package nextstep.exception;

public class ThemeReservedException extends RuntimeException {
    public ThemeReservedException() {
        this("예약되어 있는 테마는 삭제할 수 없습니다.");
    }
    public ThemeReservedException(String message) {
        super(message);
    }
}
