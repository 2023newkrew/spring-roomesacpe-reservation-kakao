package nextstep.service;

import nextstep.dto.ReservationRequestDTO;
import nextstep.dto.ReservationResponseDTO;

public interface ReservationService {
    Long create(ReservationRequestDTO request);

    ReservationResponseDTO getById(Long id);

    void deleteById(Long id);
}
