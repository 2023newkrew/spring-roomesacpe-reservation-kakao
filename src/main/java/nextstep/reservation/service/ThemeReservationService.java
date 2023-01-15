package nextstep.reservation.service;

import nextstep.reservation.dto.ReservationDetail;
import nextstep.reservation.dto.ReservationDto;

import java.util.Optional;

public interface ThemeReservationService {

    Long reserve(ReservationDto reservationDto);

    void cancelById(Long id);

    Optional<ReservationDetail> findById(Long id);
}
