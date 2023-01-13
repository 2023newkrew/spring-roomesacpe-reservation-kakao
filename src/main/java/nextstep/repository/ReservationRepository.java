package nextstep.repository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import nextstep.dto.ReservationRequestDTO;
import nextstep.entity.Reservation;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository {

    Long save(ReservationRequestDTO reservationRequestDTO) throws SQLException;

    Optional<Reservation> findById(Long id) throws SQLException;

    boolean existByDateAndTimeAndThemeId(LocalDate date, LocalTime time, Long themeId) throws SQLException;

    int deleteById(Long id) throws SQLException;
}
