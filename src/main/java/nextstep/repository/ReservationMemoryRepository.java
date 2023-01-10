package nextstep.repository;

import nextstep.Reservation;
import nextstep.exception.DuplicateReservationException;

import java.util.HashMap;
import java.util.Map;

public class ReservationMemoryRepository implements ReservationRepository {
    private final Map<Long, Reservation> reservations = new HashMap<>();
    private Long reservationIdIndex = 0L;



    @Override
    public Reservation add(Reservation newReservation) {
        validateDateTime(newReservation);

        newReservation.setId(reservationIdIndex++);
        reservations.put(newReservation.getId(), newReservation);
        return newReservation;
    }

    private void validateDateTime(Reservation newReservation) {
        boolean isDuplicateReservation = reservations.values().stream()
                .anyMatch(reservation -> reservation.hasSameDateTime(newReservation));
        if (isDuplicateReservation) {
            throw new DuplicateReservationException();
        }
    }

    @Override
    public Reservation get(Long id) {
        return reservations.get(id);
    }

    @Override
    public void delete(Long id) {
        reservations.remove(id);
    }
}
