package kakao.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomRuntimeException.class)
    protected ResponseEntity<ErrorResponseEntity> handleCustomException(CustomRuntimeException e) {
        return ErrorResponseEntity.toResponseEntity(e.getErrorCode());
    }
}
