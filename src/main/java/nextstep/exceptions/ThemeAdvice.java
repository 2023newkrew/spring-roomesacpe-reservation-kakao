package nextstep.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ThemeAdvice {
    @ExceptionHandler(ThemeException.class)
    public ResponseEntity handle(ThemeException themeException) {
        return ResponseEntity.badRequest().body(themeException.getMessage());
    }
}
