package nextstep.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import nextstep.model.Reservation;

public interface ReservationRepository {
    public Reservation save(Reservation reservation);

    public Optional<Reservation> findById(Long id);

    public Boolean existsByDateAndTime(LocalDate date, LocalTime time);

    public void deleteById(Long id);
}
