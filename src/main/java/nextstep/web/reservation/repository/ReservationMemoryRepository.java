package nextstep.web.reservation.repository;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nextstep.domain.Reservation;
import nextstep.web.common.repository.RoomEscapeRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationMemoryRepository implements RoomEscapeRepository<Reservation> {

    private final List<Reservation> reservations = new ArrayList<>();

    private final AtomicLong reservationIdIndex = new AtomicLong(1L);

    public Reservation findById(Long id) {
        return reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    public Long save(Reservation reservation) {
        reservations.add(reservation);
        
        return reservationIdIndex.incrementAndGet();
    }

    public void deleteById(Long id) {
        reservations.removeIf(it -> Objects.equals(it.getId(), id));
    }

    public List<Reservation> findAll() {
        return reservations;
    }
}
