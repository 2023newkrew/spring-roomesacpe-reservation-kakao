package nextstep.repository;

import nextstep.dto.Reservation;

public interface ReservationRepository {
    void createReservation(Reservation reservation);

    Reservation findReservation(long id);

    void deleteReservation(long id);
}
