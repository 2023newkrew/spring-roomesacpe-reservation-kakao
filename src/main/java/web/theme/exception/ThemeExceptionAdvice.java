package web.theme.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import web.exception.ErrorResponse;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ThemeExceptionAdvice {

    @ExceptionHandler(ThemeException.class)
    public ResponseEntity<ErrorResponse> handleThemeException(ThemeException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ErrorResponse.from(e.getMessage()));
    }
}
