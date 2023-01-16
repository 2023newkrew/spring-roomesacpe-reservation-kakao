package roomescape.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    DUPLICATED_RESERVATION(HttpStatus.CONFLICT, "해당 시간에 이미 예약이 있습니다."),
    NO_SUCH_RESERVATION(HttpStatus.NOT_FOUND, "해당 예약이 존재하지 않습니다."),
    ALREADY_RESERVED_THEME(HttpStatus.CONFLICT, "이미 예약되어 있는 테마는 변경할 수 없습니다."),
    DUPLICATED_THEME(HttpStatus.CONFLICT, "이미 존재하는 테마입니다."),
    NO_SUCH_THEME(HttpStatus.NOT_FOUND, "해당 테마가 존재하지 않습니다."),
    INVALID_RESERVATION_THEME(HttpStatus.BAD_REQUEST, "존재하지 않는 테마를 예약할 수 없습니다."),
    ;

    private final HttpStatus status;

    private final String message;

    ErrorCode(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }
}
