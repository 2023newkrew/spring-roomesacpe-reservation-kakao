package nextstep.service;

import nextstep.Reservation;
import nextstep.dto.ReservationRequest;

public interface ReservationService {

    Long create(ReservationRequest request);

    Reservation getById(Long id);

    void deleteById(Long id);
}
