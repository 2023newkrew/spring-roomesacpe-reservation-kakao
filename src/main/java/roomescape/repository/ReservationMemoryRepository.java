package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

import java.time.LocalDateTime;
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
    public Reservation save(Reservation reservation) {
        Long id = reservationIdIndex.getAndIncrement();
        Reservation inserted = new Reservation(id, reservation.getDateTime(), reservation.getName(), reservation.getThemeId());
        reservations.put(id, inserted);
        return inserted;
    }

    @Override
    public Optional<Reservation> find(Long id) {
        return Optional.ofNullable(reservations.get(id));
    }

    @Override
    public Boolean delete(Long id) {
        return reservations.remove(id) != null;
    }

    @Override
    public Boolean existsByDateTime(LocalDateTime dateTime) {
        return reservations.values()
                .stream()
                .anyMatch(reservation -> reservation.getDateTime().equals(dateTime));
    }
}
