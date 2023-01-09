package nextstep.repository;

import java.sql.SQLException;
import nextstep.dto.ReservationRequestDTO;
import nextstep.dto.ReservationResponseDTO;

public interface ReservationRepository {

    Long save(ReservationRequestDTO reservationRequestDTO) throws SQLException;

    ReservationResponseDTO findById(Long id) throws SQLException;

    void deleteById(Long id) throws SQLException;
}
