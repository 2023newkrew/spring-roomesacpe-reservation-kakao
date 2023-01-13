package nextstep.reservation.repository;

import nextstep.reservation.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ReservationRepository {
    Reservation save(Reservation reservation);

    Reservation findById(long id);

    Boolean findByDateAndTime(LocalDate date, LocalTime time);

    Boolean deleteById(long id);

    void clear();
}
