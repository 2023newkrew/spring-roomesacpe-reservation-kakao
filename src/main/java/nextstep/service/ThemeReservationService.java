package nextstep.service;

import nextstep.dto.ReservationDetail;
import nextstep.dto.ReservationDto;

import java.sql.SQLException;

public interface ThemeReservationService {

    Long reserve(ReservationDto reservationDto) throws SQLException;

    void cancelById(Long id) throws SQLException;

    ReservationDetail findById(Long id) throws SQLException;
}
