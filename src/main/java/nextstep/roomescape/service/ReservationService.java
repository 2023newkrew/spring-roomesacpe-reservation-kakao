package nextstep.roomescape.service;

import nextstep.roomescape.controller.ResponseDTO.ReservationResponseDTO;
import nextstep.roomescape.controller.RequestDTO.ReservationRequestDTO;
import nextstep.roomescape.repository.model.Reservation;

import java.util.List;

public interface ReservationService {
    Long create(ReservationRequestDTO reservation);

    ReservationResponseDTO findById(long id);

    void delete(long id);

    List<Reservation> findByThemeId(Long id);
}
