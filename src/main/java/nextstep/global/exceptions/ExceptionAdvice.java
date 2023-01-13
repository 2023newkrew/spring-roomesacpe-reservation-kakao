package nextstep.global.exceptions;

import nextstep.global.exceptions.exception.DuplicatedDateAndTimeException;
import nextstep.global.exceptions.exception.DuplicatedNameThemeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(DuplicatedDateAndTimeException.class)
    public ResponseEntity<String> handleDuplicatedDateAndTimeException() {
        return ResponseEntity.badRequest().body("이미 예약된 날짜와 시간입니다.");
    }

    @ExceptionHandler(DuplicatedNameThemeException.class)
    public ResponseEntity<String> handleDuplicatedNameThemeException() {
        return ResponseEntity.badRequest().body("이미 존재하는 이름의 테마입니다.");
    }
}