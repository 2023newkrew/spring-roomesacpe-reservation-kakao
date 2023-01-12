package nextstep.repository;

import nextstep.domain.Reservation;
import nextstep.dto.ReservationDTO;

import java.util.List;

public interface ReservationRepository {
    Reservation create(Reservation reservation);

    Reservation find(long id);

    List<Reservation> findAll();

    boolean delete(long id);

    boolean duplicate(ReservationDTO reservationDTO);
}
