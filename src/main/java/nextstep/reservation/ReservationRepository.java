package nextstep.reservation;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationRepository {
    private static final Map<Long, Reservation> reservationList = new HashMap<>();
    private static final AtomicLong reservationCount = new AtomicLong(1);

    public Reservation create(Reservation reservation) {
        if (findByDateTime(reservation.getDate(), reservation.getTime())) {
            throw new RuntimeException("이미 예약이 만료된 날짜/시간 입니다.");
        }
        Long id = reservationCount.getAndIncrement();
        Reservation creatteReservation = new Reservation(id, reservation.getDate(), reservation.getTime(), reservation.getName(), reservation.getTheme());
        reservationList.put(id, creatteReservation);
        return creatteReservation;
    }


    public Reservation findById(long id) {
        return reservationList.getOrDefault(id, null);
    }

    public Boolean findByDateTime(LocalDate date, LocalTime time) {
        return reservationList
                .values()
                .stream()
                .anyMatch(reservation -> reservation.getDate().equals(date) && reservation.getTime().equals(time));
    }

    public Boolean delete(long id) {
        if (reservationList.containsKey(id)) {
            reservationList.remove(id);
            return true;
        }
        return false;
    }

    public void clear(){
        reservationList.clear();
        reservationCount.set(1L);
    }
}
