package roomescape.repository;

import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface ReservationRepository {
    long save(Reservation reservation);
    Optional<Reservation> findOneById(long reservationId);
    void delete(long reservationId);
    Boolean hasOneByDateAndTime(LocalDate date, LocalTime time);
    Boolean hasReservationOfTheme(long themeId);
}
