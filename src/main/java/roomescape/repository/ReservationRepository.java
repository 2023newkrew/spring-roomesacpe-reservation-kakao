package roomescape.repository;

import roomescape.domain.Reservation;

import java.util.Optional;

public interface ReservationRepository {
    Long createReservation(Reservation reservation);

    Optional<Reservation> findById(long reservationId);

    Integer findByDateAndTime(Reservation reservation);

    Integer deleteReservation(long deleteId);
}
