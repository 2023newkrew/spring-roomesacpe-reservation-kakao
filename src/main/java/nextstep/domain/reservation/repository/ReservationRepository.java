package nextstep.domain.reservation.repository;

import nextstep.domain.reservation.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation);
    Optional<Reservation> findById(Long reservationId);
    boolean existsByDateAndTime(LocalDate date, LocalTime time);
    boolean deleteById(Long reservationId);

}
