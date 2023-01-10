package kakao.repository;

import kakao.domain.Reservation;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Repository
public class ReservationRepository {
    private final Map<Long, Reservation> reservations = new TreeMap<>();

    private Long reservationCount = 1L;

    public void save(Reservation reservation) {
        if (Objects.isNull(reservation.getId())) {
            reservation.setId(reservationCount);
        }
        reservations.put(reservationCount++, reservation);
    }

    public Reservation findById(Long id) {
        return reservations.getOrDefault(id, null);
    }

    public List<Reservation> findByDateAndTime(LocalDate date, LocalTime time) {
        return reservations.values().stream().filter(reservation -> reservation.isDuplicate(date, time)).collect(Collectors.toList());
    }

    public void delete(Long id) {
        reservations.remove(id);
    }
}
