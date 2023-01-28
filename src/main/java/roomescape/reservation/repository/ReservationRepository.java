package roomescape.reservation.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.entity.ThemeReservation;

public interface ReservationRepository {

    Long save(Reservation reservation);

    Optional<ThemeReservation> findById(Long reservationId);

    boolean deleteById(Long reservationId);

    Optional<Reservation> findByDateTimeAndThemeId(LocalDate date, LocalTime time, Long themeId);

    Optional<Reservation> findByThemeId(Long themeId);
}
