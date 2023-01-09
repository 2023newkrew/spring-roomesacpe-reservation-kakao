package web.repository;

import org.springframework.stereotype.Repository;
import web.entity.Reservation;
import web.exception.ReservationDuplicateException;

import java.util.HashMap;

@Repository
public class ReservationRepository {

    static final HashMap<Long, Reservation> reservations = new HashMap<>();
    private static long createdId = 0L;

    public long save(Reservation reservation) {
        if (isDuplicateReservation(reservation)) {
            throw new ReservationDuplicateException();
        }
        reservations.put(++createdId, reservation);
        return createdId;
    }

    private boolean isDuplicateReservation(Reservation reservation) {
        return reservations.values()
                .stream()
                .anyMatch(savedReservation -> savedReservation.isDuplicate(reservation));
    }
}
