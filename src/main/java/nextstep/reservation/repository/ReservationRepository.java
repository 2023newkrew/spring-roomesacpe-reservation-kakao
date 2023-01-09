package nextstep.reservation.repository;

import nextstep.reservation.entity.Reservation;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ReservationRepository {

    private final Map<Long, Reservation> reservationMap = new HashMap<>();

    public void add(Long id, Reservation reservation) {
        reservationMap.put(id, reservation);
    }

    public long getLastId() {
         return reservationMap.keySet().size();
    }

    public Reservation getReservation(Long id) {
        return reservationMap.get(id);
    }

}
