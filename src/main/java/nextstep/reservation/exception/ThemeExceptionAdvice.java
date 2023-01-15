package nextstep.reservation.exception;

import nextstep.exception.ConstraintViolationException;
import nextstep.exception.EntityNotFoundException;
import nextstep.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ThemeExceptionAdvice {
    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ErrorResponse> entityNotFoundException(RuntimeException err){
        err.printStackTrace();
        return ResponseEntity.internalServerError().build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<ErrorResponse> entityNotFoundException(EntityNotFoundException err){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(err.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ErrorResponse> contraintViolationException(ConstraintViolationException err){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(err.getMessage()));
    }
}
