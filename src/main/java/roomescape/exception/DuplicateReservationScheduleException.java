package roomescape.exception;

public class DuplicateReservationScheduleException extends IllegalArgumentException {

    public DuplicateReservationScheduleException() {
    }

    public DuplicateReservationScheduleException(String s) {
        super(s);
    }

}
