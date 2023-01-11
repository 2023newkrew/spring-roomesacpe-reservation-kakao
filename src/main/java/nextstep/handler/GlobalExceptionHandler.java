package nextstep.handler;

import nextstep.exception.BaseException;
import nextstep.exception.DatabaseServerException;
import nextstep.exception.ExceptionMetadata;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BaseException.class)
    private ResponseEntity<BaseException> BaseException(BaseException e) {

        return ResponseEntity
                .status(e.getHttpStatus())
                .body(e);
    }

    @ExceptionHandler(value = {SQLException.class, DataAccessException.class})
    private ResponseEntity<BaseException> DatabaseConnectionException(SQLException e) {

        return ResponseEntity
                .internalServerError()
                .body(new DatabaseServerException(ExceptionMetadata.UNEXPECTED_DATABASE_SERVER_ERROR));
    }
}
