package web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class ThemeDeleteNotAllowException extends RuntimeException {
    public ThemeDeleteNotAllowException() {
        super("현 상황에서 테마를 삭제할 수 없습니다.");
    }
}
