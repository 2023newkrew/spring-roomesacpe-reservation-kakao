package kakao.repository.reservation;

import kakao.domain.Reservation;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Repository
@Profile("in_memory")
public class ReservationInMemoryRepository implements ReservationRepository {
    private final Map<Long, Reservation> reservations = new TreeMap();

    private Long reservationCount = 1L;

    public Reservation save(Reservation reservation) {
        if (Objects.isNull(reservation.getId())) {
            reservation.setId(reservationCount);
        }
        reservations.put(reservationCount, reservation);
        reservation.setId(reservationCount++);
        return reservation;
    }

    public Reservation findById(Long id) {
        return reservations.getOrDefault(id, null);
    }

    public List<Reservation> findByThemeIdAndDateAndTime(Long themeId, LocalDate date, LocalTime time) {
        return reservations.values().stream()
                .filter(reservation -> reservation.isDuplicate(date, time) && Objects.equals(reservation.getTheme().getId(), themeId))
                .collect(Collectors.toList());
    }

    public int delete(Long id) {
        Reservation deletedReservation = reservations.remove(id);
        return Objects.isNull(deletedReservation) ? 1 : 0;
    }
}
