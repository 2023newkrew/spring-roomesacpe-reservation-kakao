package nextstep.roomescape.reservation.service;

import nextstep.roomescape.reservation.controller.dto.ReservationResponseDTO;
import nextstep.roomescape.reservation.controller.dto.ReservationRequestDTO;

public interface ReservationService {
    ReservationResponseDTO create(ReservationRequestDTO reservation);

    ReservationResponseDTO findById(long id);

    Boolean delete(long id);
}
