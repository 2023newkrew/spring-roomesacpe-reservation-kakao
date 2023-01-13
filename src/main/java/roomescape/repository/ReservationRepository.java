package roomescape.repository;

import roomescape.model.Reservation;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ReservationRepository {
    Long save(Reservation reservation);

    Optional<Reservation> find(Long id);

    Integer delete(Long id);

    Boolean existsByDateTime(LocalDateTime datetime);
}
