package nextstep.reservation.service;

import nextstep.reservation.dto.ReservationDetail;
import nextstep.reservation.dto.ReservationDto;

import java.sql.SQLException;

public interface ThemeReservationService {

    Long reserve(ReservationDto reservationDto) throws SQLException;

    void cancelById(Long id) throws SQLException;

    ReservationDetail findById(Long id) throws SQLException;
}
