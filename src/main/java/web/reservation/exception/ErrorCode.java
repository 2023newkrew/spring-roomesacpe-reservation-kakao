package web.reservation.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static web.reservation.controller.ReservationController.BEGIN_TIME;
import static web.reservation.controller.ReservationController.LAST_TIME;

@Getter
public enum ErrorCode {

    OUT_OF_BUSINESS_HOURS("영업 시간이 아닙니다. 영업 시간: " + BEGIN_TIME + " ~ " + LAST_TIME, HttpStatus.BAD_REQUEST),
    NOT_UNIT_OF_30_MINUTES("예약은 30분 단위로 가능합니다.", HttpStatus.BAD_REQUEST),
    RESERVATION_DUPLICATE("요청하신 시간대에 이미 예약이 있습니다.", HttpStatus.CONFLICT),
    RESERVATION_NOT_FOUND("예약 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
