package nextstep.main.java.nextstep.global.exception;

import nextstep.main.java.nextstep.global.exception.error.Error;
import nextstep.main.java.nextstep.global.exception.error.ErrorResponse;
import nextstep.main.java.nextstep.global.exception.exception.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException exception) {
        return handleExceptionInternal(exception.getError());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return ResponseEntity.badRequest().body(generateMethodArgumentExceptionResponseBody(exception));
    }

    private ResponseEntity<Object> handleExceptionInternal(Error error) {
        return ResponseEntity.status(error.getHttpStatus())
                .body(new ErrorResponse(error.getErrorCode(), error.getErrorMessage()));
    }

    private List<BadRequestInfo> generateMethodArgumentExceptionResponseBody(MethodArgumentNotValidException exception) {
        return exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(errors -> new BadRequestInfo(errors.getField(), errors.getDefaultMessage()))
                .collect(Collectors.toList());
    }
}
