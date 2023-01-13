package web.reservation.repository;


import web.entity.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    long save(Reservation reservation);

    Optional<Reservation> findById(long reservationId);

    List<Reservation> findAllByThemeId(long themeId);

    Long delete(long reservationId);

    void clearAll();
}
