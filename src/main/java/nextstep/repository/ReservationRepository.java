package nextstep.repository;

import nextstep.Reservation;
import nextstep.exception.ReservationNotFoundException;

public interface ReservationRepository {
    Reservation add(Reservation reservation);
    Reservation get(Long id) throws ReservationNotFoundException;
    void delete(Long id);
}
