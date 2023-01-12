package nextstep.repository;

import nextstep.domain.Reservation;
import nextstep.exception.ReservationNotFoundException;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ReservationMemoryRepository implements ReservationRepository {
    private final Map<Long, Reservation> reservations = new HashMap<>();
    private Long reservationIdIndex = 0L;



    @Override
    public Reservation add(Reservation newReservation) {
        newReservation.setId(reservationIdIndex++);
        reservations.put(newReservation.getId(), newReservation);
        return newReservation;
    }

    @Override
    public boolean hasReservationAt(LocalDate date, LocalTime time) {
        return reservations.values().stream()
                .anyMatch(reservation -> reservation.isAtDateTime(date, time));
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
