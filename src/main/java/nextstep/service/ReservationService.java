package nextstep.service;

import java.sql.SQLException;
import nextstep.dto.ReservationRequestDTO;

public interface ReservationService {

    Long createReservation(ReservationRequestDTO reservationRequestDTO) throws SQLException;
}
