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

    Long save(ReservationRequestDTO reservationRequestDTO);

    Optional<Reservation> findById(Long id);

    boolean existByDateAndTimeAndThemeId(LocalDate date, LocalTime time, Long themeId);

    int deleteById(Long id);
}
