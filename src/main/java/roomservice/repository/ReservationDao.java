package roomservice.repository;

import roomservice.domain.Reservation;

public interface ReservationDao {
    long insertReservation(Reservation reservation);
    Reservation selectReservation(long id);
    void deleteReservation(long id);
}
