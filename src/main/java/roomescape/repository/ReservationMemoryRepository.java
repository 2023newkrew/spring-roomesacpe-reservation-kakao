package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ReservationMemoryRepository implements ReservationRepository{
    private final static Map<Long, Reservation> reservations = new HashMap<>();
    private static Long reservationIdIndex = 0L;

    @Override
    public long save(Reservation reservation) {
        reservation.setId(reservationIdIndex);
        reservations.put(reservationIdIndex, reservation);
        return reservationIdIndex++;
    }

    @Override
    public Optional<Reservation> findOneById(long reservationId) {
        return Optional.ofNullable(reservations.get(reservationId));
    }

    @Override
    public void delete(long reservationId) {
        reservations.remove(reservationId);
    }

    @Override
    public Boolean hasOneByDateAndTime(LocalDate date, LocalTime time) {
        return reservations.values()
                .stream()
                .anyMatch(reservation -> reservation.getDate().equals(date) && reservation.getTime().equals(time));
    }
}
