package nextstep.exception;

public class NegativeThemePriceException extends RuntimeException {
    public NegativeThemePriceException() {
        super("가격은 음수가 될 수 없음");
    }
}
