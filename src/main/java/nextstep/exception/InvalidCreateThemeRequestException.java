package nextstep.exception;

public class InvalidCreateThemeRequestException extends RuntimeException{

    public InvalidCreateThemeRequestException() {
        super("테마 생성시 이름, 설명, 가격을 입력해야 합니다.");
    }
}
