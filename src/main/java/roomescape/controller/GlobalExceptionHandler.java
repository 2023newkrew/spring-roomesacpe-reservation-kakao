package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessageDto> duplicateReservationHandler(IllegalArgumentException e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().body(new ErrorMessageDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorMessageDto> noReservationFoundHandler(NoSuchElementException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessageDto(e.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    public static class ErrorMessageDto {
        private final String errorMessage;
        private final int httpStatus;

        public ErrorMessageDto(String errorMessage, int httpStatus) {
            this.errorMessage = errorMessage;
            this.httpStatus = httpStatus;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public int getHttpStatus() {
            return httpStatus;
        }
    }
}
