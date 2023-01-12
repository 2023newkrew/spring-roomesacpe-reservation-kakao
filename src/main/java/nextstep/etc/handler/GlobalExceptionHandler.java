package nextstep.etc.handler;

import nextstep.etc.exception.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> errorHandler(Throwable e) {
        BaseException baseException = getBaseException(e);

        return new ResponseEntity<>(baseException, baseException.getHttpStatus());

    }

    private BaseException getBaseException(Throwable e) {
        if (e instanceof BaseException) {
            return (BaseException) e;
        }

        return new BaseException(e);
    }
}
