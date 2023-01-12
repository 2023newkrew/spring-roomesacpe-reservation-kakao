package nextstep.reservation.repository;

import nextstep.reservation.domain.Reservation;

public interface ReservationRepository {

    Long insertIfNotExistsDateTime(Reservation reservation);

    Reservation getById(Long id);

    boolean deleteById(Long id);
}
