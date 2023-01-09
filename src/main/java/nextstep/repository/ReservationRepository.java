package nextstep.repository;

import java.sql.SQLException;
import nextstep.dto.ReservationRequestDTO;

public interface ReservationRepository {

    Long save(ReservationRequestDTO reservationRequestDTO) throws SQLException;
}
