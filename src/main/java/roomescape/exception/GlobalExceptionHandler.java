package roomescape.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.dto.ErrorResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RoomEscapeException.class)
    public ResponseEntity<ErrorResponseDto> roomEscapeExceptionHandler(Exception e) {
        String message = e.getMessage();
        if (message.equals(ErrorCode.NO_SUCH_ELEMENT.getMessage())) {
            ErrorResponseDto res = new ErrorResponseDto(
                    ErrorCode.NO_SUCH_ELEMENT.toString(),
                    message
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }
        else if (message.equals(ErrorCode.RESERVATION_DATETIME_ALREADY_EXISTS.getMessage())) {
            ErrorResponseDto res = new ErrorResponseDto(
                    ErrorCode.RESERVATION_DATETIME_ALREADY_EXISTS.toString(),
                    message
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }
        else if (message.equals(ErrorCode.THEME_NAME_ALREADY_EXISTS.getMessage())) {
            ErrorResponseDto res = new ErrorResponseDto(
                    ErrorCode.THEME_NAME_ALREADY_EXISTS.toString(),
                    message
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }
        else {
            ErrorResponseDto res = new ErrorResponseDto(
                    ErrorCode.INTERNAL_SERVER_ERROR.toString(),
                    ErrorCode.INTERNAL_SERVER_ERROR.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }
}
