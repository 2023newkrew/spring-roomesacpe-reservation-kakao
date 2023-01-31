package nextstep.domain.reservation.repository;

import nextstep.domain.reservation.domain.Reservation;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationMemoryRepository implements ReservationRepository {
    private Map<Long, Reservation> reservations = new HashMap<>();
    private final AtomicLong reservationId = new AtomicLong(0L);

    @Override
    public Long save(Reservation reservation) {
        reservation.setId(reservationId.incrementAndGet());
        reservations.put(reservationId.get(), reservation);

        return reservationId.get();
    }

    @Override
    public int countByDateAndTime(LocalDate date, LocalTime time) {
        return (int) reservations.values().stream()
                .filter(reservation ->
                        reservation.getDate().equals(date) && reservation.getTime().equals(time))
                .count();
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        if (!reservations.containsKey(id)) {
            return Optional.empty();
        }
        return Optional.ofNullable(reservations.get(id));
    }

    @Override
    public void delete(Long id) {
        reservations.remove(id);
    }

    @Override
    public List<Reservation> findByTheme(String name) {
        return null;
    }
}
