package nextstep.repository;

import nextstep.domain.Reservation;
import nextstep.exception.ReservationNotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ReservationRepository {
    Reservation add(Reservation reservation);
    Reservation findById(Long id) throws ReservationNotFoundException;
    void deleteById(Long id);
    boolean hasReservationAt(LocalDate date, LocalTime time);
}
