package nextstep.service;

import java.sql.SQLException;
import nextstep.dto.ReservationRequestDTO;
import nextstep.dto.ReservationResponseDTO;

public interface ReservationService {

    Long createReservation(ReservationRequestDTO reservationRequestDTO) throws SQLException;

    ReservationResponseDTO findReservation(Long id) throws SQLException;

    void deleteById(Long id) throws SQLException;
}
