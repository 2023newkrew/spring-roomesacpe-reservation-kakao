package nextstep.service;

import nextstep.dto.ReservationRequestDTO;
import nextstep.dto.ReservationResponseDTO;

import java.sql.SQLException;

public interface ReservationService {

    Long createReservation(ReservationRequestDTO reservationRequestDTO) throws SQLException;

    ReservationResponseDTO findReservation(Long id) throws SQLException;

    void deleteById(Long id) throws SQLException;
}
