package nextstep.web.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 리소스를 찾을 수 없습니다."),

    SQL_CONNECTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SQL 연결 실패");

    private final HttpStatus httpStatus;

    private final String message;
}
