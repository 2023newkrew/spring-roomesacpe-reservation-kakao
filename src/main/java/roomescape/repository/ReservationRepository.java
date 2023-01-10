package roomescape.repository;

import roomescape.nextstep.Reservation;

import java.util.Optional;

public interface ReservationRepository {
    void insertReservation(Reservation reservation);
    Optional<Reservation> getReservation(Long id);
    int deleteReservation(Long id);
}
