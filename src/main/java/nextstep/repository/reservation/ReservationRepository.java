package nextstep.repository.reservation;

import nextstep.domain.Reservation;
import nextstep.exception.ReservationNotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ReservationRepository {
    Reservation add(Reservation reservation);
    Reservation get(Long id) throws ReservationNotFoundException;
    void delete(Long id);
    boolean hasReservationAt(LocalDate date, LocalTime time);
}
