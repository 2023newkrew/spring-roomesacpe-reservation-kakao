package nextstep.repository;

import nextstep.Reservation;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationDao {
    List<Reservation> reservations = new ArrayList<>();

    public void save(Reservation reservation) {
        reservation.setId((long) (reservations.size() + 1));
        reservations.add(reservation);
    }

    public Reservation findById(Long id) {
        return reservations.get((int) (id - 1));
    }
}
