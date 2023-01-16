package nextstep.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ErrorResponse> entityNotFoundException(RuntimeException err){
        return ResponseEntity.internalServerError().build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<ErrorResponse> entityNotFoundException(EntityNotFoundException err){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(err.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ErrorResponse> constraintViolationException(ConstraintViolationException err){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(err.getMessage()));
    }
}
