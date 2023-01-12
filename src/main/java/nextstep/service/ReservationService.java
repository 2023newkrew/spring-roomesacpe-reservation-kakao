package nextstep.service;

import java.sql.SQLException;
import nextstep.dto.ReservationRequestDTO;
import nextstep.dto.ReservationResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface ReservationService {

    Long createReservation(ReservationRequestDTO reservationRequestDTO) throws SQLException;

    ReservationResponseDTO findReservation(Long id) throws SQLException;

    void deleteById(Long id) throws SQLException;
}
