package nextstep.main.java.nextstep.repository;

import nextstep.main.java.nextstep.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface ReservationRepository {
    void save(Reservation reservation);

    Reservation findOne(Long id);

    void deleteOne(Long id);

    Optional<Reservation> findByDateAndTime(LocalDate date, LocalTime time);
}
