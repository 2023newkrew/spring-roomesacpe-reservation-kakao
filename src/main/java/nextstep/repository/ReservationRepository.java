package nextstep.repository;

import nextstep.dto.Reservation;

import java.util.List;

public interface ReservationRepository {
    Reservation create(Reservation reservation);

    Reservation find(long id);

    List<Reservation> findAll();

    boolean delete(long id);
}
