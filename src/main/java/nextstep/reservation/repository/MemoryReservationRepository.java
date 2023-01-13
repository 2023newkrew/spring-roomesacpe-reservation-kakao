package nextstep.reservation.repository;

import nextstep.reservation.entity.Reservation;
import nextstep.reservation.exception.ReservationException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static nextstep.reservation.exception.ReservationExceptionCode.DUPLICATE_TIME_RESERVATION;


public class MemoryReservationRepository implements ReservationRepository {
    private static final Map<Long, Reservation> reservationList = new HashMap<>();
    private static final AtomicLong reservationCount = new AtomicLong(1);

    @Override
    public Reservation save(Reservation reservation) {
        if (findByDateAndTime(reservation.getDate(), reservation.getTime())) {
            throw new ReservationException(DUPLICATE_TIME_RESERVATION);
        }
        Long id = reservationCount.getAndIncrement();
        Reservation creatteReservation = new Reservation(id, reservation.getDate(), reservation.getTime(), reservation.getName(), reservation.getThemeId());
        reservationList.put(id, creatteReservation);
        return creatteReservation;
    }

    @Override
    public Reservation findById(long id) {
        return reservationList.getOrDefault(id, null);
    }
    @Override
    public Boolean findByDateAndTime(LocalDate date, LocalTime time) {
        return reservationList
                .values()
                .stream()
                .anyMatch(reservation -> reservation.getDate().equals(date) && reservation.getTime().equals(time));
    }

    @Override
    public Boolean deleteById(long id) {
        if (reservationList.containsKey(id)) {
            reservationList.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public void clear(){
        reservationList.clear();
        reservationCount.set(1L);
    }
}
