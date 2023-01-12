package web.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(RuntimeException ignore) {
        return ResponseEntity.internalServerError()
                .body(ErrorResponse.from("서버에 문제가 생겼습니다."));
    }
}
