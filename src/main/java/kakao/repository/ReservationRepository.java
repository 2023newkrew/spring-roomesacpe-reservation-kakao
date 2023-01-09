package kakao.repository;

import kakao.domain.Reservation;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

@Repository
public class ReservationRepository {
    private final Map<Long, Reservation> reservations = new TreeMap();

    private Long reservationCount = 1L;

    public void save(Reservation reservation) {
        if (Objects.isNull(reservation.getId())) {
            reservation.setId(reservationCount);
        }
        reservations.put(reservationCount++, reservation);
    }

    public Reservation findById(Long id) {
        return reservations.getOrDefault(id, null);
    }
}
