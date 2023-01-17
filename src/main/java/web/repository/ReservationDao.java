package web.repository;

import web.entity.Reservation;

import java.util.Optional;

public interface ReservationDao {
    Long save(Reservation reservation);

    boolean isDuplicateReservation(Reservation reservation);

    Optional<Reservation> findById(long reservationId);

    Long delete(long reservationId);

    void clearAll();

    Boolean isExistReservationByThemeId(long themeId);
}
