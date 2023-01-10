package nextstep.repository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import nextstep.dto.ReservationRequestDTO;
import nextstep.dto.ReservationResponseDTO;

public interface ReservationRepository {

    Long save(ReservationRequestDTO reservationRequestDTO) throws SQLException;

    Optional<ReservationResponseDTO> findById(Long id) throws SQLException;

    boolean existByDateAndTime(LocalDate date, LocalTime time) throws SQLException;

    int deleteById(Long id) throws SQLException;
}
