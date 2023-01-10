package kakao.error;

import kakao.error.exception.DuplicatedReservationException;
import kakao.error.exception.RecordNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 담당 컨트롤러조차 발견하지 못했을 경우 발생
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException e) {
        return handlerException(ErrorCode.API_NOT_FOUND);
    }

    // 담당 컨트롤러는 존재하나 해당하는 컨트롤러 메소드가 없을 시 발생
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return handlerException(ErrorCode.API_NOT_FOUND);
    }

    // url에 해당하는 컨트롤러 메소드는 존재하나 Http 메소드가 옳바르지 않을 시 발생
    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowedException(MethodNotAllowedException e) {
        return handlerException(ErrorCode.API_NOT_FOUND);
    }

    //Custom

    // 이미 예약이 되어 있는 시간대에 예약을 시도할 경우 발생
    @ExceptionHandler(DuplicatedReservationException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatedReservationException(DuplicatedReservationException e) {
        return handlerException(e.getErrorCode());
    }

    // 해당 id의 row가 존재하지 않을 시 발생
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRecordNotFoundException(RecordNotFoundException e) {
        return handlerException(e.getErrorCode());
    }

    private ResponseEntity<ErrorResponse> handlerException(ErrorCode errorCode) {
        ErrorResponse errorResponse = ErrorResponse.of(errorCode);
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }
}
