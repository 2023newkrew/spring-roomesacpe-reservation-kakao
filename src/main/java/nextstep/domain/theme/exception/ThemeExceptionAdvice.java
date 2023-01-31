package nextstep.domain.theme.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ThemeExceptionAdvice {

    @ExceptionHandler(DuplicatedNameThemeException.class)
    public ResponseEntity<String> handleDuplicatedNameThemeException() {
        return ResponseEntity.badRequest().body("이미 존재하는 이름의 테마입니다.");
    }

    @ExceptionHandler(ThemeNotFoundException.class)
    public ResponseEntity<String> handleThemeNotFoundException() {
        return ResponseEntity.badRequest().body("존재하지 않는 테마입니다.");
    }

    @ExceptionHandler(ReservedThemeModifyException.class)
    public ResponseEntity<String> handleReservedThemeModifyException() {
        return ResponseEntity.badRequest().body("예약이 존재하는 테마는 수정 및 삭제할 수 없습니다.");
    }
}
