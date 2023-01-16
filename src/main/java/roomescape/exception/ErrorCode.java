package roomescape.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    RESERVATION_NOT_FOUND("예약이 존재하지 않습니다", HttpStatus.NOT_FOUND),
    THEME_NOT_FOUND("테마가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    TIME_TABLE_NOT_AVAILABLE("예약할 수 없는 시간입니다", HttpStatus.BAD_REQUEST),
    DUPLICATED_RESERVATION("중복된 예약이 존재합니다", HttpStatus.CONFLICT),
    THEME_HAS_RESERVATION("해당 테마에 대한 예약이 있어 수정하거나 삭제할 수 없습니다.", HttpStatus.CONFLICT);

    private final String message;
    private final HttpStatus httpStatus;
}
