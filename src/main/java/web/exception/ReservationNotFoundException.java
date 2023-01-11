package web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(long id) {
        super("ID가 " + id + "인 예약 정보를 찾을 수 없습니다.");
    }
}
