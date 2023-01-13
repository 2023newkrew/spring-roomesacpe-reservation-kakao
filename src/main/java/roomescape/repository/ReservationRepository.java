package roomescape.repository;

import roomescape.domain.Reservation;

public interface ReservationRepository {

    Long addReservation(Reservation reservation);
    int checkSchedule(String date, String time);
    Reservation findReservation(Long id);
    int removeReservation(Long id);

}
