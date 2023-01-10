package web.exception;

import java.time.DateTimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import web.dto.response.ErrorResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<ErrorResponseDTO> handleDateTimeException() {
        return ResponseEntity.badRequest()
                .body(ErrorResponseDTO.from("날짜 형식이 일치하지 않습니다."));
    }

    @ExceptionHandler(NoSuchReservationException.class)
    public ResponseEntity<ErrorResponseDTO> handleNoSucReservationException(NoSuchReservationException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDTO.from(e.getMessage()));
    }

    @ExceptionHandler(DuplicatedReservationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDuplicatedReservationException(DuplicatedReservationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponseDTO.from(e.getMessage()));
    }
}
