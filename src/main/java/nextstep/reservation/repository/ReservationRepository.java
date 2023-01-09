package nextstep.reservation.repository;

import nextstep.reservation.entity.Reservation;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ReservationRepository {

    private final Map<Long, Reservation> reservationMap = new HashMap<>();

    public void delete(final Long id) {
        reservationMap.remove(id);
    }

    public void add(Long id, Reservation reservation) {
        reservationMap.put(id, reservation);
    }

    public long getLastId() {
         return reservationMap.keySet().size();
    }

    public Reservation getReservation(Long id) {
        return reservationMap.get(id);
    }

    public Optional<Reservation> findByDateAndTime(final LocalDate date, final LocalTime time) {
        return reservationMap.values()
                .stream()
                .filter(reservation -> reservation.getDate().equals(date) && reservation.getTime().equals(time))
                .findAny();
    }
}
