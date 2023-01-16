package kakao.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "Reservation not found."),
    DUPLICATE_RESERVATION(HttpStatus.CONFLICT, "Duplicated reservation.");

    private final HttpStatus httpStatus;
    private final String message;
}
