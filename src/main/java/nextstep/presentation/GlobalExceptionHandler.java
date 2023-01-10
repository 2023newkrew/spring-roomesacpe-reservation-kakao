package nextstep.presentation;

import nextstep.dto.response.ErrorResponse;
import nextstep.error.ApplicationException;
import nextstep.error.ErrorType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleReservationNotFoundException(ApplicationException e) {
        ErrorType errorType = e.getErrorType();
        ErrorResponse errorResponse = new ErrorResponse(errorType);

        return ResponseEntity.status(errorType.getStatus())
                .body(errorResponse);
    }

}
