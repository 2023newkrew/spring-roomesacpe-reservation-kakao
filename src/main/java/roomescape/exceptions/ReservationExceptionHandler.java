package roomescape.exceptions;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.exceptions.exception.DuplicatedReservationException;
import roomescape.exceptions.exception.DuplicatedThemeException;
import roomescape.exceptions.exception.NoThemesExistException;
import roomescape.exceptions.exception.ReservationNotFoundException;
import roomescape.exceptions.exception.ThemeNotFoundException;

@ControllerAdvice
public class ReservationExceptionHandler {
    @ExceptionHandler(DuplicatedReservationException.class)
    public ResponseEntity handleDuplicatedReservationException(DuplicatedReservationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(DuplicatedThemeException.class)
    public ResponseEntity handleDuplicatedThemeException(DuplicatedThemeException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<String> handleReservationNotFoundException(ReservationNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ThemeNotFoundException.class)
    public ResponseEntity<String> handleThemeNotFoundException(ThemeNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoThemesExistException.class)
    public ResponseEntity<String> handleNoThemesExistException(NoThemesExistException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDataAccessException() {
        return ResponseEntity.internalServerError().body("Internal Server Error");
    }
}
