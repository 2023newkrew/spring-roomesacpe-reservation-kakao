package roomescape.repository;

import roomescape.domain.Reservation;

public interface ReservationRepository {

    public Long addReservation(Reservation reservation);
    public int checkSchedule(String date, String time);
    public Reservation findReservation(Long id);
    public int removeReservation(Long id);

}
