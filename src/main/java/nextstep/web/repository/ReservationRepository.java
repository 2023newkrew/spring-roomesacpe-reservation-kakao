package nextstep.web.repository;

import nextstep.domain.Reservation;

public interface ReservationRepository {

    Reservation findById(Long id);

    Long save(Reservation reservation);

    void deleteById(Long id);
}
