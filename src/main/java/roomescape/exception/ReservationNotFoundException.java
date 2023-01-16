package roomescape.exception;

import java.util.NoSuchElementException;

public class ReservationNotFoundException extends NoSuchElementException {

    public ReservationNotFoundException() {
    }

    public ReservationNotFoundException(String s) {
        super(s);
    }

}
