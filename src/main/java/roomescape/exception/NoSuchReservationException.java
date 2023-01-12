package roomescape.exception;

public class NoSuchReservationException extends RuntimeException {

    public NoSuchReservationException() {
        super("해당 예약이 존재하지 않습니다.");
    }
}
