package nextstep.controller;

import nextstep.exception.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<BaseException> BaseException(BaseException e) {
        return new ResponseEntity<>(e, e.getErrorStatus());
    }
}
