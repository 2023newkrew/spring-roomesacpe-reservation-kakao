package nextstep.exception;

import nextstep.dto.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import static nextstep.exception.ErrorCode.INTERNAL_SERVER_ERROR;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ReservationException.class)
    public ErrorResponse reservationExceptionHandler(ReservationException e) {
        return new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
    }

    // 위에서 잡히지 않는 예외들은 모두 INTERNAL_SERVER_ERROR로 응답한다.
    @ExceptionHandler(value = Exception.class)
    public ErrorResponse exceptionHandler() {
        return new ErrorResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR.getDescription());
    }
}
