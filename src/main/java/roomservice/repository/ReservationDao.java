package roomservice.repository;

import roomservice.domain.Reservation;

public interface ReservationDao {
    long add(Reservation reservation);
    Reservation findById(long id);
    void deleteById(long id);
}
