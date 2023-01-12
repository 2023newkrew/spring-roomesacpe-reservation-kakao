package nextstep.reservation.service;

import nextstep.reservation.domain.Reservation;
import nextstep.reservation.dto.ReservationRequest;

public interface ReservationService {

    Long create(ReservationRequest request);

    Reservation getById(Long id);

    void deleteById(Long id);
}
