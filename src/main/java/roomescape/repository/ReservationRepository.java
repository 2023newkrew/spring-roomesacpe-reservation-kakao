package roomescape.repository;

import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface ReservationRepository {
    Long save(Reservation reservation);
    Optional<Reservation> find(Long id);
    Integer delete(Long id);
    Boolean has(LocalDate date, LocalTime time);
}
