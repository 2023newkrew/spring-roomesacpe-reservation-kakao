package roomescape.repository;

import roomescape.model.Reservation;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ReservationRepository {
    Reservation save(Reservation reservation);

    Optional<Reservation> find(Long id);

    Boolean delete(Long id);

    Boolean existsByDateTime(LocalDateTime datetime);
}
