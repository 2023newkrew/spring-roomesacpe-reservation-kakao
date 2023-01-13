package nextstep.exceptions;

import nextstep.exceptions.exception.DuplicatedDateAndTimeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(DuplicatedDateAndTimeException.class)
    public ResponseEntity<String> handle() {
        return ResponseEntity.badRequest().body("이미 예약된 날짜와 시간입니다.");
    }
}
