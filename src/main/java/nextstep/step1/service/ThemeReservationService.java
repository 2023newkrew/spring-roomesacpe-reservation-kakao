package nextstep.step1.service;

import nextstep.step1.dto.ReservationDetail;
import nextstep.step1.dto.ReservationDto;

public interface ThemeReservationService {

    public Long reserve(ReservationDto reservationDto);

    public void cancelById(Long id);

    public ReservationDetail findById(Long id);
}
