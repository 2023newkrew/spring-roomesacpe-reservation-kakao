package nextstep.roomescape.reservation.service;

import nextstep.roomescape.reservation.controller.dto.ReservationResponseDTO;
import nextstep.roomescape.reservation.controller.dto.ReservationRequestDTO;

public interface ReservationService {
    Long create(ReservationRequestDTO reservation);

    ReservationResponseDTO findById(long id);

    void delete(long id);
}
