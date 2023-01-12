package nextstep.repository;

import nextstep.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface ReservationRepository {
    Reservation save(Reservation reservation);
    Optional<Reservation> findById(Long id);
    Boolean existsByDateAndTime(LocalDate date, LocalTime time);
    void deleteById(Long id);
}
