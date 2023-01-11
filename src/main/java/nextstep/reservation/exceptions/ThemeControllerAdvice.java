package nextstep.reservation.exceptions;

import nextstep.reservation.controller.ThemeController;
import nextstep.reservation.exceptions.exception.DuplicateThemeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = ThemeController.class)
public class ThemeControllerAdvice {

    @ExceptionHandler(DuplicateThemeException.class)
    public ResponseEntity<String> duplicateTheme() {
        return ResponseEntity
                .badRequest()
                .body("이미 동일한 이름의 테마가 있습니다.");
    }
}
