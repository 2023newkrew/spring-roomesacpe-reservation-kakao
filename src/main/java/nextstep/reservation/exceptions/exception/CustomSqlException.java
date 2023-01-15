package nextstep.reservation.exceptions.exception;

import org.springframework.http.HttpStatus;

public class CustomSqlException extends RestAPIException {

    public CustomSqlException() {
        this("SQL 에러가 발생하였습니다.");
    }

    public CustomSqlException(String responseMessage) {
        super(responseMessage);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
