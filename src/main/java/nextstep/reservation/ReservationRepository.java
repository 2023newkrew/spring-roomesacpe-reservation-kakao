package nextstep.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class ReservationRepository {
    private static final Map<Long, Reservation> reservationList = new HashMap<>();
    private static final AtomicLong reservationCount = new AtomicLong(1);
    public void create(LocalDate date, LocalTime time, String name, Theme theme) {
        if (findByDateTime(date, time)){
            throw new RuntimeException("이미 예약이 만료된 날짜/시간 입니다.");
        }
        Long id = reservationCount.getAndIncrement();
        reservationList.put(id, new Reservation(id, date, time, name, theme));
    }

    public Map<Long, Reservation> getReservationList() {
        return reservationList;
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
