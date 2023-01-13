package nextstep.reservation.repository;

import nextstep.reservation.domain.Reservation;

public interface ReservationRepository {

    boolean existsByDateAndTime(Reservation reservation);

    Long insert(Reservation reservation);

    Reservation getById(Long id);

    boolean deleteById(Long id);
}
