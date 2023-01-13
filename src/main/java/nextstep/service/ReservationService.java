package nextstep.service;

import nextstep.dto.ReservationRequestDTO;
import nextstep.dto.ReservationResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface ReservationService {

    Long createReservation(ReservationRequestDTO reservationRequestDTO);

    ReservationResponseDTO findReservation(Long id);

    void deleteById(Long id);

    boolean existByThemeId(Long id);
}
