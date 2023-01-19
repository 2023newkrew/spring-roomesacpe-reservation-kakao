package nextstep.web;

import nextstep.exception.ReservationDuplicateException;
import nextstep.exception.ReservationNotFoundException;
import nextstep.exception.ThemeNotFoundException;
import nextstep.web.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler({
            ReservationDuplicateException.class,
            ReservationNotFoundException.class,
            ThemeNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> badRequest(Exception e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> internalServerError() {
        ErrorResponse response = new ErrorResponse("요청을 처리할 수 없습니다.");
        return ResponseEntity.internalServerError().body(response);
    }
}
