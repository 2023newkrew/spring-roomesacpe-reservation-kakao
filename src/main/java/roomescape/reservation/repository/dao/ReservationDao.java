package roomescape.reservation.repository.dao;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;

@Repository
public interface ReservationDao {
    Long save(Reservation reservation);
    Optional<Reservation> findById(Long id);
    int delete(Long id);
    boolean isReservationDuplicated(Reservation reservation);
}
