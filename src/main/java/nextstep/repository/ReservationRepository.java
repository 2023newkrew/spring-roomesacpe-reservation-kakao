package nextstep.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import nextstep.model.Reservation;

public interface ReservationRepository {
    Reservation save(Reservation reservation);

    Optional<Reservation> findById(Long id);

    boolean existsByDateAndTimeAndThemeId(LocalDate date, LocalTime time, Long themeId);

    void deleteById(Long id);

    void deleteAll();
}
