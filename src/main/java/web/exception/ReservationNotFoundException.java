package web.exception;

public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException(long id) {
        super("ID가 " + id + "인 예약 정보를 찾을 수 없습니다.");
    }
}
