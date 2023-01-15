package roomescape.repository.Reservation;

import roomescape.domain.Reservation;

import java.util.Optional;

public interface ReservationRepository {
    Long createReservation(Reservation reservation);

    Optional<Reservation> findReservationById(long reservationId);

    Integer findIdByDateAndTime(Reservation reservation);

    Integer deleteReservation(long deleteId);
}
