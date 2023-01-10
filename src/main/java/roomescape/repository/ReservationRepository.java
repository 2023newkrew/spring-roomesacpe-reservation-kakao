package roomescape.repository;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface ReservationRepository {
    void insertReservation(Reservation reservation);
    Optional<Reservation> getReservation(Long id);
    void deleteReservation(Long id);
    Optional<Reservation> getReservationByDateAndTime(LocalDate date, LocalTime time);
}
