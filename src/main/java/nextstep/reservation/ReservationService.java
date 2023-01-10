package nextstep.reservation;

import nextstep.reservation.domain.Reservation;

public interface ReservationService {
    Reservation create(Reservation reservation);

    Reservation findById(long id);

    Boolean delete(long id);
}
