package web.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    RESERVATION_DUPLICATE("요청하신 시간대에 이미 예약이 있습니다.", HttpStatus.CONFLICT),
    RESERVATION_NOT_FOUND("예약 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
