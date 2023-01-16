package nextstep.exception;

import nextstep.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import static nextstep.exception.ErrorCode.INTERNAL_SERVER_ERROR;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ReservationException.class)
    public ResponseEntity<ErrorResponse> reservationExceptionHandler(ReservationException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    // 위에서 잡히지 않는 예외들은 모두 INTERNAL_SERVER_ERROR로 응답한다.
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler() {
        ErrorResponse errorResponse = new ErrorResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR.getDescription());
        return ResponseEntity.internalServerError().body(errorResponse);
    }
}
