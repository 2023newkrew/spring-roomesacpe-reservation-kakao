package nextstep.repository;

import nextstep.domain.Reservation;
import nextstep.exception.ReservationNotFoundException;

import java.time.LocalDate;
import java.util.Optional;

public interface ReservationRepository {
    Reservation save(Reservation reservation);
    Optional<Reservation> findById(Long id) throws ReservationNotFoundException;
    void deleteById(Long id);
    boolean hasReservationAt(LocalDate date, int hour);
    boolean existsByThemeId(Long themeId);
}
