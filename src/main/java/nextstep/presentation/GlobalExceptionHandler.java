package nextstep.presentation;

import lombok.extern.slf4j.Slf4j;
import nextstep.dto.response.ErrorResponse;
import nextstep.error.ApplicationException;
import nextstep.error.ErrorType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String LOG_FORMAT = "errorName = {}, errorStatus = {}, errorMessage = {}";

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleReservationNotFoundException(ApplicationException e) {
        ErrorType errorType = e.getErrorType();
        ErrorResponse errorResponse = new ErrorResponse(errorType);

        log.error(LOG_FORMAT, errorType.getName(), errorType.getHttpStatus(), e.getMessage());

        return ResponseEntity.status(errorType.getHttpStatus())
                .body(errorResponse);
    }

    @ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleUnsatisfiedServletRequestParameterException(UnsatisfiedServletRequestParameterException e) {
        ErrorType errorType = ErrorType.INVALID_REQUEST_PARAMETER;
        ErrorResponse errorResponse = new ErrorResponse(errorType);

        log.error(LOG_FORMAT, errorType.getName(), errorType.getHttpStatus(), e.getMessage());

        return ResponseEntity.status(errorType.getHttpStatus())
                .body(errorResponse);
    }

}
