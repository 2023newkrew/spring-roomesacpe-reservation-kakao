package roomescape.repository;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nextstep.Reservation;
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

    public void save(Reservation reservation) {
        reservation.setId(reservationIdIndex++);
        reservations.add(reservation);
    }

    public void deleteById(Long id) {
        reservations.removeIf(it -> Objects.equals(it.getId(), id));
    }
}
