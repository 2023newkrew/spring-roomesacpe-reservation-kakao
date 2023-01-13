package nextstep.reservation.repository;

import nextstep.reservation.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    Reservation save(Reservation reservation);

    Optional<Reservation> findById(long id);

    List<Reservation> findByDateAndTime(LocalDate date, LocalTime time);

    int deleteById(long id);

    void clear();
}
