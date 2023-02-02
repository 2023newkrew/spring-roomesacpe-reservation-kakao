package nextstep.repository;

import nextstep.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ReservationRepository {
    Boolean existsByDateTime(LocalDate date, LocalTime time);

    Long insert(Reservation reservation);

    Reservation getById(Long id);

    Boolean deleteById(Long id);
}
