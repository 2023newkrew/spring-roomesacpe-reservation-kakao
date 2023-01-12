package nextstep.roomescape.reservation;

import nextstep.roomescape.reservation.domain.dto.ReservationResponseDTO;
import nextstep.roomescape.reservation.domain.entity.Reservation;
import nextstep.roomescape.reservation.domain.dto.ReservationRequestDTO;

public interface ReservationService {
    Reservation create(ReservationRequestDTO reservation);

    ReservationResponseDTO findById(long id);

    Boolean delete(long id);
}
