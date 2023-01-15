package nextstep.web.repository.memory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nextstep.domain.Reservation;
import nextstep.web.repository.ReservationRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationMemoryRepository implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();

    private Long reservationIdIndex = 1L;

    @Override
    public Reservation findById(Long id) {
        return reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public Optional<Reservation> findByThemeId(Long themeId) {
        return reservations.stream()
                .filter(it -> Objects.equals(it.getTheme()
                        .getId(), themeId))
                .findFirst();
    }

    @Override
    public Long save(Reservation reservation) {
        reservationIdIndex++;
        reservations.add(reservation);

        return reservationIdIndex;
    }

    @Override
    public void deleteById(Long id) {
        reservations.removeIf(it -> Objects.equals(it.getId(), id));
    }
}
