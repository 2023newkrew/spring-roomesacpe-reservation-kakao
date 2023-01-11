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
public class ReservationMemoryRepository implements ReservationRepository{
    private final Map<Long, Reservation> reservations;
    private final AtomicLong reservationIdIndex;

    ReservationMemoryRepository() {
        reservations = new ConcurrentHashMap<>();
        reservationIdIndex = new AtomicLong();
    }

    @Override
    public Long save(Reservation reservation) {
        reservation.setId(reservationIdIndex.get());
        reservations.put(reservationIdIndex.get(), reservation);
        return reservationIdIndex.getAndIncrement();
    }

    @Override
    public Optional<Reservation> findOneById(long reservationId) {
        return Optional.ofNullable(reservations.get(reservationId));
    }

    @Override
    public Integer delete(long reservationId) {
        reservations.remove(reservationId);
        return 1;
    }

    @Override
    public Boolean hasOneByDateAndTime(LocalDate date, LocalTime time) {
        return reservations.values()
                .stream()
                .anyMatch(reservation -> reservation.getDate().equals(date) && reservation.getTime().equals(time));
    }
}
