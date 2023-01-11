package nextstep.repository;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ReservationRepository {
    Reservation findById(Long id);

    void deleteById(Long id);

    Long save(LocalDate date, LocalTime time, String name, Theme theme);
}
