package reservation.util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reservation.util.exception.RestAPIException;


@RestControllerAdvice
public class ReservationExceptionHandler {

    // 날짜와 시간이 겹친다면 생성 불가능
    @ExceptionHandler(RestAPIException.class)
    public ResponseEntity<String> handleReservationException(RestAPIException e){
        return ResponseEntity.status(e.getHttpStatus())
                .body(e.getErrorMessage());
    }
}
