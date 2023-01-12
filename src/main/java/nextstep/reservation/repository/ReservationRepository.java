package nextstep.reservation.repository;

import nextstep.reservation.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ReservationRepository {

    boolean existsByDateAndTime(LocalDate date, LocalTime time);

    Long insert(Reservation reservation);

    Reservation getById(Long id);

    boolean deleteById(Long id);
}
