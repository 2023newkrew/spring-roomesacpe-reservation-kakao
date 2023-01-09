package nextstep.main.java.nextstep.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReservationAdvice {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleDuplicate(){
        return new ResponseEntity<>("같은 날짜와 시간에 예약이 존재합니다.",HttpStatus.CONFLICT);
    }
}
