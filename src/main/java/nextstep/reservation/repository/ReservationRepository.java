package nextstep.reservation.repository;

import nextstep.reservation.dto.Reservation;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@Repository
public class ReservationRepository {

    private final Map<Long, Reservation> reservationMap = new HashMap<>();

    public void add(Long id, Reservation reservation) {
        reservationMap.put(id, reservation);
    }

    public long getLastId() {
         return reservationMap.keySet().size();
    }

}
