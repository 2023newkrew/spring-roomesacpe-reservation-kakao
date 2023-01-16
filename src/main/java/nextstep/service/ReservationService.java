package nextstep.service;

import nextstep.Reservation;
import nextstep.dto.ReservationRequestDTO;

public interface ReservationService {

    Long create(ReservationRequestDTO request);

    Reservation getById(Long id);

    void deleteById(Long id);
}
