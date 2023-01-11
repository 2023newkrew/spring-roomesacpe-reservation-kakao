package nextstep.exception;

public class ReservationNotFoundException extends RoomEscapeException{
    public ReservationNotFoundException(Long id) {
        super(id + "번에 해당하는 예약이 없습니다.");
    }
}
