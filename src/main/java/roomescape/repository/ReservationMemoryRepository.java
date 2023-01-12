package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationMemoryRepository implements ReservationRepository {
    private final Map<Long, Reservation> reservations;
    private final AtomicLong reservationIdIndex;

    ReservationMemoryRepository() {
        reservations = new ConcurrentHashMap<>();
        reservationIdIndex = new AtomicLong();
    }

    @Override
    public Long save(Reservation reservation) {
        Long id = reservationIdIndex.getAndIncrement();
        reservation.setId(id);
        reservations.put(id, reservation);
        return id;
    }

    @Override
    public Optional<Reservation> find(Long id) {
        return Optional.ofNullable(reservations.get(id));
    }

    @Override
    public Integer delete(Long id) {
        reservations.remove(id);
        return 1;
    }

    @Override
    public Boolean has(LocalDate date, LocalTime time) {
        return reservations.values()
                .stream()
                .anyMatch(reservation -> reservation.getDate().equals(date) && reservation.getTime().equals(time));
    }
}
