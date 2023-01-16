package roomescape.common.exception.handler;

import java.time.DateTimeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.common.dto.response.ErrorResponseDTO;
import roomescape.common.exception.CustomException;
import roomescape.reservation.exception.ReservationException;
import roomescape.theme.exception.ThemeException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<ErrorResponseDTO> handleDateTimeException() {
        return ResponseEntity.badRequest()
                .body(ErrorResponseDTO.from("날짜 형식이 일치하지 않습니다."));
    }

    @ExceptionHandler(ThemeException.class)
    public ResponseEntity<ErrorResponseDTO> handleThemeException(ThemeException e) {
        return buildErrorResponse(e);
    }

    @ExceptionHandler(ReservationException.class)
    public ResponseEntity<ErrorResponseDTO> handleReservationException(ReservationException e) {
        return buildErrorResponse(e);
    }

    private ResponseEntity<ErrorResponseDTO> buildErrorResponse(CustomException e) {
        return ResponseEntity.status(e.getStatus())
                .body(ErrorResponseDTO.from(e.getMessage()));
    }
}
