package nextstep.reservation.service;

import nextstep.reservation.dto.ReservationDetail;
import nextstep.reservation.dto.ReservationDto;

public interface ThemeReservationService {

    Long reserve(ReservationDto reservationDto);

    void cancelById(Long id);

    ReservationDetail findById(Long id);
}
