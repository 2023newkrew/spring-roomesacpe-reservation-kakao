package kakao.repository;

import domain.Reservation;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Repository
public class ReservationMemRepository implements ReservationRepository {
    private final Map<Long, Reservation> reservations = new TreeMap<>();

    private long reservationCount = 0;

    @Override
    public long save(Reservation reservation) {
        if (Objects.isNull(reservation.getId())) {
            reservation.setId(reservationCount);
        }
        reservations.put(++reservationCount, reservation);
        return reservationCount;
    }

    @Override
    public Reservation findById(Long id) {
        return reservations.getOrDefault(id, null);
    }

    @Override
    public List<Reservation> findByDateAndTime(LocalDate date, LocalTime time) {
        return reservations.values().stream().filter(reservation -> reservation.isDuplicate(date, time)).collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findByThemeId(Long themeId) {
        return reservations.values().stream().filter(reservation -> themeId.equals(reservation.getThemeId())).collect(Collectors.toList());
    }

    @Override
    public int delete(Long id) {
        if (reservations.containsKey(id)) return 0;

        reservations.remove(id);
        return 1;
    }
}
