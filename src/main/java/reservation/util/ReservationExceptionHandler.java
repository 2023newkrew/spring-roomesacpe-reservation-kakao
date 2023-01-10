package reservation.util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reservation.util.exception.DuplicateException;
import reservation.util.exception.NotFoundException;

import static reservation.util.ErrorStatus.*;

@RestControllerAdvice
public class ReservationExceptionHandler {

    // 날짜와 시간이 겹친다면 생성 불가능
    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatedReservation(DuplicateException e){
        return ResponseEntity.status(e.getErrorStatus().getStatus())
                .body(new ErrorResponse(RESERVATION_DUPLICATED));
    }

    // 검색과 삭제 시 없는 id라면 삭제 불가능
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException e){
        return ResponseEntity.status(e.getErrorStatus().getStatus())
                .body(new ErrorResponse(RESERVATION_NOT_FOUND));
    }
}
