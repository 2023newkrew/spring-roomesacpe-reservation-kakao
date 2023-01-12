package nextstep.web.repository;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nextstep.domain.Reservation;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationMemoryRepository implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();

    private Long reservationIdIndex = 1L;

    public Reservation findById(Long id) {
        return reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    public Long save(Reservation reservation) {
        reservationIdIndex++;
        reservations.add(reservation);
        
        return reservationIdIndex;
    }

    public void deleteById(Long id) {
        reservations.removeIf(it -> Objects.equals(it.getId(), id));
    }
}
