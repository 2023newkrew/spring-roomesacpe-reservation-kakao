package nextstep.repository;

import nextstep.Reservation;

public interface ReservationRepository {
    Reservation add(Reservation reservation);
    Reservation get(Long id);
    void delete(Long id);
}
