package nextstep.main.java.nextstep.repository;

import nextstep.main.java.nextstep.domain.Reservation;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class MemoryReservationRepository implements ReservationRepository {
    public static Map<Long, Reservation> reservationMap = new HashMap<>();
    private static AtomicLong id = new AtomicLong(1L);

    @Override
    public Reservation save(Reservation reservation) {
        reservationMap.put(id.get(), reservation);
        return new Reservation(id.getAndIncrement(), reservation);
    }

    @Override
    public Optional<Reservation> findOne(Long id) {
        return Optional.ofNullable(reservationMap.get(id));
    }

    @Override
    public List<Reservation> findAllByThemeId(Long themeId) {
        return reservationMap.values()
                .stream()
                .filter(reservation -> reservation.getThemeId()
                        .equals(themeId))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean deleteOne(Long id) {
        return Objects.nonNull(reservationMap.remove(id));
    }

    @Override
    public Boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        return reservationMap.values()
                .stream()
                .anyMatch(x -> x.getDate()
                        .equals(date) && x.getTime()
                        .equals(time));
    }
}
