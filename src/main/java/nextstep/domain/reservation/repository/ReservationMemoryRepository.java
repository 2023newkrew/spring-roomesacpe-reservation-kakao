package nextstep.domain.reservation.repository;

import nextstep.domain.reservation.domain.Reservation;
import nextstep.global.exceptions.exception.ReservationNotFoundException;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationMemoryRepository implements ReservationRepository {
    private Map<Long, Reservation> reservations = new HashMap<>();
    private final AtomicLong atomicLong = new AtomicLong(0L);

    public Long save(Reservation reservation) {
        reservation.setId(atomicLong.incrementAndGet());
        reservations.put(atomicLong.get(), reservation);

        return atomicLong.get();
    }

    public int countByDateAndTime(LocalDate date, LocalTime time) {
        return (int) reservations.values().stream()
                .filter(reservation ->
                        reservation.getDate().equals(date) && reservation.getTime().equals(time))
                .count();
    }

    public Reservation findById(Long id) {
        if (!reservations.containsKey(id)) {
            throw new ReservationNotFoundException();
        }
        return reservations.get(id);
    }

    public void delete(Long id) {
        reservations.remove(id);
    }
}
