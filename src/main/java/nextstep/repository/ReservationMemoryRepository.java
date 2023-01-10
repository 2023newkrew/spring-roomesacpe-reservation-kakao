package nextstep.repository;

import nextstep.Reservation;
import nextstep.exception.DuplicateReservationException;
import nextstep.exception.ReservationNotFoundException;

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
    public Reservation get(Long id)  throws ReservationNotFoundException {
        Reservation reservation = reservations.get(id);

        if (reservation == null) {
            throw new ReservationNotFoundException();
        }

        return reservation;
    }

    @Override
    public void delete(Long id) {
        reservations.remove(id);
    }
}
