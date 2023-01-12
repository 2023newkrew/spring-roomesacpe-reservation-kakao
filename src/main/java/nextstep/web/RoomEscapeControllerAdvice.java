package nextstep.web;

import nextstep.exception.RoomEscapeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RoomEscapeControllerAdvice {
    @ExceptionHandler(RoomEscapeException.class)
    public ResponseEntity<String> handleRoomEscapeException(RoomEscapeException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }
}
