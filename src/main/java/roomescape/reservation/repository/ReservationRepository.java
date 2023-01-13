package roomescape.reservation.repository;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;

@Repository
public interface ReservationRepository {
    Long save(Reservation reservation);

    Optional<Reservation> findById(Long reservationId);

    int delete(Long reservationId);

    boolean isReservationDuplicated(Reservation reservation);
}
