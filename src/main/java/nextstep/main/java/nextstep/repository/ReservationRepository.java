package nextstep.main.java.nextstep.repository;

import nextstep.main.java.nextstep.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    Reservation save(Reservation reservation);

    Optional<Reservation> findOne(Long id);

    List<Reservation> findAllByThemeId(Long themeId);

    Boolean deleteOne(Long id);

    Boolean existsByDateAndTime(LocalDate date, LocalTime time);
}
