package roomescape.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.dto.ErrorResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RoomEscapeException.class)
    public ResponseEntity<ErrorResponseDto> roomEscapeExceptionHandler(RoomEscapeException e) {
        String message = e.getMessage();
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus statusCode = errorCode.getStatusCode();
        ErrorResponseDto res = new ErrorResponseDto(errorCode.toString(), message);
        return ResponseEntity.status(statusCode).body(res);
    }
}
