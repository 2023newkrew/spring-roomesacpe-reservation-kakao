package nextstep.exception;

public class ReservedThemeDeleteException extends RuntimeException{
    public ReservedThemeDeleteException() {
        super("예약중인 테마는 삭제할 수 없습니다.");
    }
}
