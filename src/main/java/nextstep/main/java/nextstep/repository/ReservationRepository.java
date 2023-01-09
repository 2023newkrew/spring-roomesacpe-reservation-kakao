package nextstep.main.java.nextstep.repository;

import nextstep.main.java.nextstep.domain.Reservation;

public interface ReservationRepository {
    void save(Reservation reservation);

    Reservation findOne(Long id);

    void deleteOne(Long id);
}
