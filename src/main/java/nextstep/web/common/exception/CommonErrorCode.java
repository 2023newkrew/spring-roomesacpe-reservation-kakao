package nextstep.web.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 리소스를 찾을 수 없습니다."),

    SQL_CONNECTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SQL 연결 실패."),

    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),

    BAD_PARAMETER_REQUEST(HttpStatus.BAD_REQUEST, "처리할 수 없는 요청입니다."),

    RESERVED_THEME_ERROR(HttpStatus.METHOD_NOT_ALLOWED, "예약 있는 테마는 수정/삭제가 불가능합니다.");

    private final HttpStatus httpStatus;

    private final String message;
}
