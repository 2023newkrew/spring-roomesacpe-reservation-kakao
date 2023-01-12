package roomescape.repository;

import roomescape.entity.Reservation;

public interface ReservationDao {
    long add(Reservation reservation);
    Reservation findById(long id);
    void deleteById(long id);
}
