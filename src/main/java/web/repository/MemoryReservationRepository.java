package web.repository;

import web.entity.Reservation;
import web.exception.ReservationDuplicateException;

import java.util.HashMap;
import java.util.Optional;

public class MemoryReservationRepository implements ReservationRepository {

    static final HashMap<Long, Reservation> reservations = new HashMap<>();
    private static long createdId = 0L;

    @Override
    public Long save(Reservation reservation) {
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

    @Override
    public Optional<Reservation> findById(long reservationId) {
        return Optional.ofNullable(reservations.get(reservationId));
    }

    @Override
    public Long delete(long reservationId) {
        return reservations.remove(reservationId) == null ? 0L : 1L;
    }

    @Override
    public void clearAll() {
        reservations.clear();
    }
}
