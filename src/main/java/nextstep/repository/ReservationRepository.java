package nextstep.repository;

import nextstep.Reservation;
import nextstep.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ReservationRepository {
    Reservation findById(Long id);

    void deleteById(Long id);

    Reservation save(LocalDate date, LocalTime time, String name, Theme theme);
}
