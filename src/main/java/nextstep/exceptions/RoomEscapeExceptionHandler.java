package nextstep.exceptions;

import nextstep.exceptions.exception.InvalidRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class RoomEscapeExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<String> handleInvalidInputException(InvalidRequestException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
}
