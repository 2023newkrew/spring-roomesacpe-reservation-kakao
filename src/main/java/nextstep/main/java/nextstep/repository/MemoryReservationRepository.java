package nextstep.main.java.nextstep.repository;

import nextstep.main.java.nextstep.domain.Reservation;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MemoryReservationRepository implements ReservationRepository {
    static Map<Long, Reservation> reservationMap = new HashMap<>();

    @Override
    public void save(Reservation reservation) {
        reservationMap.put(reservation.getId(), reservation);
    }

    @Override
    public Reservation findOne(Long id) {
        return reservationMap.get(id);
    }

    @Override
    public void deleteOne(Long id) {
        reservationMap.remove(id);
    }
}
