package nextstep.main.java.nextstep.mvc.repository.reservation;

import nextstep.main.java.nextstep.mvc.domain.reservation.Reservation;
import nextstep.main.java.nextstep.mvc.domain.reservation.request.ReservationCreateRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@Deprecated(forRemoval = true)
public class MemoryReservationRepository implements ReservationRepository {
    public static Map<Long, Reservation> reservationMap = new HashMap<>();
    private static Long count = 1L;

    @Override
    public Long save(ReservationCreateRequest request) {
        return null;
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return Optional.ofNullable(reservationMap.get(id));
    }

    @Override
    public void deleteById(Long id) {
        reservationMap.remove(id);
    }

    @Override
    public Boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        return reservationMap.values().stream()
                .anyMatch(x -> x.getDate().equals(date) && x.getTime().equals(time));
    }

    @Override
    public Boolean existsById(Long id) {
        return null;
    }
}
