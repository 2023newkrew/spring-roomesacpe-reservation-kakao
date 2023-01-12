package roomescape.repository;

import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface ReservationRepository {
    Long save(Reservation reservation);
    Optional<Reservation> findOneById(long reservationId);
    Integer delete(long reservationId);
    Boolean hasOneByDateAndTime(LocalDate date, LocalTime time);
}
