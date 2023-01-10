package nextstep.service;

import nextstep.Reservation;
import nextstep.dto.ReservationRequest;

public interface ReservationService {

    Reservation create(ReservationRequest request);

    Reservation getById(Long id);

    void deleteById(Long id);
}
