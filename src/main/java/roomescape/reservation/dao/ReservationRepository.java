package roomescape.reservation.dao;

import roomescape.reservation.domain.Reservation;

public interface ReservationRepository {
    Reservation add(Reservation reservation);

    Reservation get(Long id);

    Reservation get(String date, String time);

    void remove(Long id);
}
