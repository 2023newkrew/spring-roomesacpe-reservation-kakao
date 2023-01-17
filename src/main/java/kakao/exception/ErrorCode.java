package kakao.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "Reservation not found."),
    THEME_NOT_FOUND(HttpStatus.NOT_FOUND, "Theme not found."),
    DUPLICATE_RESERVATION(HttpStatus.CONFLICT, "Duplicated reservation."),
    DUPLICATE_THEME(HttpStatus.CONFLICT, "Duplicated theme."),
    CORRESPONDING_RESERVATION_EXIST(HttpStatus.CONFLICT, "Corresponding reservation exist. Could not remove theme");

    private final HttpStatus httpStatus;
    private final String message;
}
