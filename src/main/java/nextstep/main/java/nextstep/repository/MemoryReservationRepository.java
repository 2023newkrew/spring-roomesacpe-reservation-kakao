package nextstep.main.java.nextstep.repository;

import nextstep.main.java.nextstep.domain.Reservation;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MemoryReservationRepository implements ReservationRepository {
    Map<Integer, Reservation> reservationMap = new HashMap<>();

    @Override
    public void save(Reservation reservation) {
        reservationMap.put(Math.toIntExact(reservation.getId()), reservation);
    }
}
