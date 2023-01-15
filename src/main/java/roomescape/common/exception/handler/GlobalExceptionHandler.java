package roomescape.common.exception.handler;

import java.time.DateTimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.reservation.dto.response.ErrorResponseDTO;
import roomescape.reservation.exception.DuplicatedReservationException;
import roomescape.reservation.exception.NoSuchReservationException;
import roomescape.theme.exception.AlreadyReservedThemeException;
import roomescape.theme.exception.DuplicatedThemeException;
import roomescape.theme.exception.NoSuchThemeException;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(e.getBindingResult().getFieldErrors());
    }

    @ExceptionHandler(DuplicatedThemeException.class)
    public ResponseEntity<ErrorResponseDTO> handleDuplicatedThemeException(DuplicatedThemeException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponseDTO.from(e.getMessage()));
    }

    @ExceptionHandler(NoSuchThemeException.class)
    public ResponseEntity<ErrorResponseDTO> handleNoSuchThemeException(NoSuchThemeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDTO.from(e.getMessage()));
    }

    @ExceptionHandler(AlreadyReservedThemeException.class)
    public ResponseEntity<ErrorResponseDTO> handleAlreadyReservedThemeException(AlreadyReservedThemeException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponseDTO.from(e.getMessage()));
    }
}
