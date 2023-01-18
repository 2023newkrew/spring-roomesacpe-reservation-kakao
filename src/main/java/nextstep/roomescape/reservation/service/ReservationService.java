package nextstep.roomescape.reservation.service;

import nextstep.roomescape.reservation.controller.dto.ReservationResponseDTO;
import nextstep.roomescape.reservation.controller.dto.ReservationRequestDTO;
import nextstep.roomescape.reservation.repository.model.Reservation;

import java.util.List;

public interface ReservationService {
    Long create(ReservationRequestDTO reservation);

    ReservationResponseDTO findById(long id);

    void delete(long id);

    List<Reservation> findByThemeId(Long id);
}
