package nextstep.service;

import nextstep.dto.ReservationRequestDTO;
import nextstep.entity.Reservation;
import nextstep.entity.Theme;
import org.springframework.stereotype.Service;

@Service
public interface ReservationService {

    Reservation createReservation(ReservationRequestDTO reservationRequestDTO, Theme theme);

    void validateCreateReservation(ReservationRequestDTO reservationRequestDTO);

    Reservation findReservationByID(Long id);

    void deleteById(Long id);

    boolean existByThemeId(Long id);

}
