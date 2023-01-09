package nextstep.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class ReservationRepository {
    private static final Map<Long, Reservation> reservationList = new HashMap<>();
    private static final AtomicLong reservationCount = new AtomicLong(1);
    public void create(String date, String time, String name, Theme theme) {
        Long id = reservationCount.getAndIncrement();
        reservationList.put(id, new Reservation(id, LocalDate.parse(date), LocalTime.parse(time), name, theme));
    }

    public Map<Long, Reservation> getReservationList() {
        return reservationList;
    }

    public Reservation findById(long l) {
        if (reservationList.containsKey(l)){
            return reservationList.get(l);
        }
        return null;
    }
}
