package nextstep.step3.reservation.service;

import nextstep.step3.reservation.dto.ReservationDetail;
import nextstep.step3.reservation.dto.ReservationDto;

import java.sql.SQLException;

public interface ThemeReservationService {

    Long reserve(ReservationDto reservationDto) throws SQLException;

    void cancelById(Long id) throws SQLException;

    ReservationDetail findById(Long id) throws SQLException;
}
