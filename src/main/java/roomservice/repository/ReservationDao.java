package roomservice.repository;

import roomservice.domain.Reservation;
import roomservice.exception.DuplicatedReservationException;
import roomservice.exception.NonExistentReservationException;
import java.util.HashMap;
import java.util.Map;

public class ReservationDao {
    private final Map<Long, Reservation> cache = new HashMap<>();
    private long id = 1L;

    public long insertReservation(Reservation reservation) {
        validateDuplication(reservation);
        reservation.setId(id);
        cache.put(id, reservation);
        return id++;
    }

    public Reservation selectReservation(long id) {
        validateExistence(id);
        return cache.get(id);
    }

    public void deleteReservation(long id) {
        validateExistence(id);
        cache.remove(id);
    }

    private void validateDuplication(Reservation reservation) {
        for (Reservation cachedReservation : cache.values()) {
            validateDuplicationForSingleReservation(reservation, cachedReservation);
        }
    }

    private void validateDuplicationForSingleReservation(Reservation reservation, Reservation cachedReservation) {
        if (reservation.getTime().equals(cachedReservation.getTime()) &&
                reservation.getDate().equals(cachedReservation.getDate())) {
            throw new DuplicatedReservationException();
        }
    }

    private void validateExistence(long id) {
        if (!cache.containsKey(id)) {
            throw new NonExistentReservationException();
        }
    }
}
