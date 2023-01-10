package web.repository;


import web.entity.Reservation;

import java.util.Optional;

public interface ReservationRepository {

    long save(Reservation reservation);

    Optional<Reservation> findById(long reservationId);

    Long delete(long reservationId);
}
