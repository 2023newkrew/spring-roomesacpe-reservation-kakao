package nextstep.exception;

public class InvalidCreateReservationRequestException extends RuntimeException {

    public InvalidCreateReservationRequestException() {
        super("예약 생성 시에는 날짜, 시간, 이름, 테마 번호를 모두 기입해야 합니다.");
    }

}
