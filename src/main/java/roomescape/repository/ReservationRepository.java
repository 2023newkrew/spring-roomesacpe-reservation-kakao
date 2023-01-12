package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import roomescape.domain.Reservation;

public interface ReservationRepository {

    public Long save(Reservation reservation);

    public Optional<Reservation> findById(Long reservationId);

    public boolean deleteById(Long reservationId);

    public Optional<Reservation> findByDateAndTime(LocalDate date, LocalTime time);
}
