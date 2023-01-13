package nextstep.repository;

import nextstep.Reservation;

public interface ReservationRepository {

    Long insertIfNotExistsDateTime(Reservation reservation);

    Reservation getById(Long id);

    boolean deleteById(Long id);
}
