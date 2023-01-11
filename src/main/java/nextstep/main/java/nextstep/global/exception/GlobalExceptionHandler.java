package nextstep.main.java.nextstep.global.exception;

import nextstep.main.java.nextstep.global.exception.error.Error;
import nextstep.main.java.nextstep.global.exception.error.ErrorResponse;
import nextstep.main.java.nextstep.global.exception.exception.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleDuplicate(ApiException exception) {
        return handleExceptionInternal(exception.getError());
    }

    private ResponseEntity<Object> handleExceptionInternal(Error error) {
        return ResponseEntity.status(error.getHttpStatus())
                .body(new ErrorResponse(error.getErrorCode(), error.getErrorMessage()));
    }
}
