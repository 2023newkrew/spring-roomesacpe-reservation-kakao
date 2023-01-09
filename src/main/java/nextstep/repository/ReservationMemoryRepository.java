package nextstep.repository;

import nextstep.Reservation;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ReservationMemoryRepository implements ReservationRepository {
    private final Map<Long, Reservation> reservations = new HashMap<>();
    private Long reservationIdIndex = 0L;



    @Override
    public Reservation add(Reservation reservation) {
        reservation.setId(reservationIdIndex++);
        reservations.put(reservation.getId(), reservation);
        return reservation;
    }

    @Override
    public Reservation get(Long id) {
        return reservations.get(id);
    }

    @Override
    public void delete(Long id) {

    }
}
