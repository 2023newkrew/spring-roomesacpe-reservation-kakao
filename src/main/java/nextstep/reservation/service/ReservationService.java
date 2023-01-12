package nextstep.reservation.service;

import nextstep.reservation.dto.ReservationDTO;
import nextstep.reservation.dto.ReservationRequest;

public interface ReservationService {

    Long create(ReservationRequest request);

    ReservationDTO getById(Long id);

    boolean deleteById(Long id);
}
