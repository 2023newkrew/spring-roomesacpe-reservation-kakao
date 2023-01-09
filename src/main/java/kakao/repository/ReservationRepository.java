package kakao.repository;

import kakao.domain.Reservation;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

@Repository
public class ReservationRepository {
    private final Set<Reservation> reservations = new TreeSet<>();

    private Long reservationCount = 0L;

    public void save(Reservation reservation) {
        if (Objects.isNull(reservation.getId())) {
            reservation.setId(reservationCount++);
        }
        reservations.add(reservation);
    }
}
