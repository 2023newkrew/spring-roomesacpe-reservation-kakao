package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import roomescape.domain.Reservation;

public interface ReservationRepository {

    Long save(Reservation reservation);

    Optional<Reservation> findById(Long reservationId);

    boolean deleteById(Long reservationId);

    Optional<Reservation> findByDateAndTime(LocalDate date, LocalTime time);
}
