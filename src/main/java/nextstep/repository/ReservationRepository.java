package nextstep.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import nextstep.entity.Reservation;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository {

    Reservation save(Reservation reservation);

    Optional<Reservation> findById(Long id);

    boolean existByDateAndTimeAndThemeId(LocalDate date, LocalTime time, Long themeId);

    int deleteById(Long id);

    boolean existByThemeId(Long id);
}
