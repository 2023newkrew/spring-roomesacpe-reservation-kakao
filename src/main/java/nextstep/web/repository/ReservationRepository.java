package nextstep.web.repository;

import nextstep.domain.Reservation;

public interface ReservationRepository {

    Reservation findById(Long id);

    void save(Reservation reservation);

    void deleteById(Long id);
}
