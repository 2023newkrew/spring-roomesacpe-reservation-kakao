package nextstep.step2.service;

import nextstep.step2.dto.ReservationDetail;
import nextstep.step2.dto.ReservationDto;

import java.sql.SQLException;

public interface ThemeReservationService {

    Long reserve(ReservationDto reservationDto) throws SQLException;

    void cancelById(Long id) throws SQLException;

    ReservationDetail findById(Long id) throws SQLException;
}
