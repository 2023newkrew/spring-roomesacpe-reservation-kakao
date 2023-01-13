package nextstep.reservation.repository;

import nextstep.reservation.entity.Reservation;
import nextstep.reservation.exception.RoomEscapeException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static nextstep.reservation.exception.RoomEscapeExceptionCode.DUPLICATE_TIME_RESERVATION;


public class MemoryReservationRepository implements ReservationRepository {
    private static final Map<Long, Reservation> reservationList = new HashMap<>();
    private static final AtomicLong reservationCount = new AtomicLong(1);

    @Override
    public Reservation save(Reservation reservation) {
        if (findByDateAndTime(reservation.getDate(), reservation.getTime()).size() > 0) {
            throw new RoomEscapeException(DUPLICATE_TIME_RESERVATION);
        }
        Long id = reservationCount.getAndIncrement();
        Reservation creatteReservation = new Reservation(id, reservation.getDate(), reservation.getTime(), reservation.getName(), reservation.getThemeId());
        reservationList.put(id, creatteReservation);
        return creatteReservation;
    }

    @Override
    public Optional<Reservation> findById(long id) {
        return Optional.ofNullable(reservationList.getOrDefault(id, null));
    }

    @Override
    public List<Reservation> findByDateAndTime(LocalDate date, LocalTime time) {
        return reservationList
                .values()
                .stream()
                .filter(reservation -> reservation.getDate().equals(date) && reservation.getTime().equals(time))
                .collect(Collectors.toList());
    }

    @Override
    public int deleteById(long id) {
        if (reservationList.containsKey(id)) {
            reservationList.remove(id);
            return 1;
        }
        return 0;
    }

    @Override
    public void clear(){
        reservationList.clear();
        reservationCount.set(1L);
    }
}
