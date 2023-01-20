package roomescape.repository.Reservation;

import roomescape.domain.Reservation;

import java.util.Optional;

public interface ReservationRepository {
    Long createReservation(Reservation reservation);

    Optional<Reservation> findReservationById(long reservationId);

    Boolean isExistsByDateAndTime(Reservation reservation);

    Integer deleteReservation(long deleteId);

    Boolean isReservation(Long themeId);

}
