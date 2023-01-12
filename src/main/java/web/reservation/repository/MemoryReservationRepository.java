package web.reservation.repository;

import web.entity.Reservation;
import web.reservation.exception.ReservationException;

import java.util.HashMap;
import java.util.Optional;

import static web.reservation.exception.ErrorCode.RESERVATION_DUPLICATE;

public class MemoryReservationRepository implements ReservationRepository {

    static final HashMap<Long, Reservation> reservations = new HashMap<>();
    private static long createdId = 0L;

    @Override
    public long save(Reservation reservation) {
        if (isDuplicateReservation(reservation)) {
            throw new ReservationException(RESERVATION_DUPLICATE);
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
