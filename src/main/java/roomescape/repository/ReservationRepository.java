package roomescape.repository;

import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface ReservationRepository {
    long save(Reservation reservation);
    Optional<Reservation> findOneById(long reservationId);
    void delete(long reservationId);
    Boolean hasOneByDateAndTimeAndTheme(LocalDate date, LocalTime time, Long themeId);
    Boolean hasReservationOfTheme(long themeId);
}
