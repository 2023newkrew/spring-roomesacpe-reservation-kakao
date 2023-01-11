package nextstep.main.java.nextstep.repository.reservation;

import nextstep.main.java.nextstep.domain.reservation.Reservation;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryReservationRepository implements ReservationRepository {
    public static Map<Long, Reservation> reservationMap = new HashMap<>();
    private static Long count = 1L;

    @Override
    public Reservation save(Reservation reservation) {
        reservationMap.put(count, reservation);
        return new Reservation(count++, reservation);
    }

    @Override
    public Optional<Reservation> findOne(Long id) {
        return Optional.ofNullable(reservationMap.get(id));
    }

    @Override
    public void deleteOne(Long id) {
        reservationMap.remove(id);
    }

    @Override
    public Boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        return reservationMap.values().stream()
                .anyMatch(x -> x.getDate().equals(date) && x.getTime().equals(time));
    }
}
