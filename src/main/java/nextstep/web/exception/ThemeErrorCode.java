package nextstep.web.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ThemeErrorCode implements ErrorCode {

    THEME_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 테마를 찾을 수 없습니다."),
    THEME_RELATED_WITH_RESERVATION(HttpStatus.BAD_REQUEST, "예약과 연관된 테마는 수정 및 삭제가 불가능합니다.");

    private final HttpStatus httpStatus;

    private final String message;
}
