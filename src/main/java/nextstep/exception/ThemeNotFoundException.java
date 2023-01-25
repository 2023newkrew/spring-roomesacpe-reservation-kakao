package nextstep.exception;

public class ThemeNotFoundException extends RuntimeException {

    public ThemeNotFoundException(Long id) {
        super(id + "번에 해당하는 테마가 없습니다.");
    }
}
