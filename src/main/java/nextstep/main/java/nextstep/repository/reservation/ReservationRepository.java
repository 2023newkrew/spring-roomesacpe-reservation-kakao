package nextstep.main.java.nextstep.repository.reservation;

import nextstep.main.java.nextstep.domain.reservation.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface ReservationRepository {
    Reservation save(Reservation reservation);

    Optional<Reservation> findOne(Long id);

    void deleteOne(Long id);

    Boolean existsByDateAndTime(LocalDate date, LocalTime time);
}
