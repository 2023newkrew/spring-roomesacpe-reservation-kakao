package nextstep.service;

import nextstep.dto.ReservationRequestDto;
import nextstep.entity.Reservation;
import nextstep.entity.Theme;
import org.springframework.stereotype.Service;

@Service
public interface ReservationService {

    Reservation createReservation(ReservationRequestDto reservationRequestDTO, Theme theme);

    void validateCreateReservation(ReservationRequestDto reservationRequestDTO);

    Reservation findReservationByID(Long id);

    void deleteById(Long id);

    boolean existByThemeId(Long id);

}
