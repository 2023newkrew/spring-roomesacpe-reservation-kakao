package nextstep.main.java.nextstep.repository;

import nextstep.main.java.nextstep.domain.Reservation;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryReservationRepository implements ReservationRepository {
    public static Map<Long, Reservation> reservationMap = new HashMap<>();

    @Override
    public void save(Reservation reservation) {
        reservationMap.put(reservation.getId(), reservation);
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
    public Optional<Reservation> findByDateAndTime(LocalDate date, LocalTime time) {
        return reservationMap.values().stream()
                .filter(x -> x.getDate().equals(date) && x.getTime().equals(time))
                .findAny();
    }
}
