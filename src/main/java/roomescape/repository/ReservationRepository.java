package roomescape.repository;

import nextstep.Reservation;

public interface ReservationRepository {

    Reservation findById(Long id);

    void save(Reservation reservation);

    void deleteById(Long id);
}
