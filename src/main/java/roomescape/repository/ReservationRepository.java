package roomescape.repository;

import java.util.Optional;
import org.springframework.stereotype.Service;
import roomescape.entity.Reservation;

@Service
public interface ReservationRepository {
    Long save(Reservation reservation);

    Optional<Reservation> findById(Long reservationId);

    int delete(Long reservationId);

    boolean isReservationIdDuplicated(Reservation reservation);
}
