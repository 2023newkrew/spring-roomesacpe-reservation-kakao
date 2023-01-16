package roomescape.controller;

import com.sun.jdi.request.DuplicateRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionHandlers {
    private static final Logger logger =
            LoggerFactory.getLogger(ExceptionHandlers.class);

    @ExceptionHandler({DuplicateRequestException.class, ArithmeticException.class})
    public ResponseEntity<Object> badRequestException(final RuntimeException ex) {
        logger.warn(ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> notFoundException(final NoSuchElementException ex) {
        logger.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(NullPointerException.class )
    public ResponseEntity<Object> lengthRequiredException(final NullPointerException ex) {
        logger.info(ex.getClass().getName());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.LENGTH_REQUIRED);
    }
}
