package roomescape.repository;

import roomescape.domain.Reservation;

import java.util.Optional;

public interface ReservationRepository {

    Long addReservation(Reservation reservation);
    int checkSchedule(String date, String time);
    Optional<Reservation> findReservation(Long id);
    int removeReservation(Long id);

}
