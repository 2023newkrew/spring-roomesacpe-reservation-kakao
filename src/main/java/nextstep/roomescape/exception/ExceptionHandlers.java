package nextstep.roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(NotExistEntityException.class)
    public ResponseEntity onException(NotExistEntityException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity onException(DuplicateEntityException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(UsedExistEntityException.class)
    public ResponseEntity onException(UsedExistEntityException e)  {
        return ResponseEntity.badRequest().build();
    }
}
