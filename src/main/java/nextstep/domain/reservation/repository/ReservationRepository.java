package nextstep.domain.reservation.repository;

import nextstep.domain.reservation.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    Long save(Reservation reservation);

    int countByDateAndTime(LocalDate date, LocalTime time);

    Optional<Reservation> findById(Long id);

    void delete(Long id);

    List<Reservation> findByTheme(String name);
}
