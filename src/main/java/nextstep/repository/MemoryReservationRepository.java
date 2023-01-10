package nextstep.repository;

import nextstep.Reservation;
import nextstep.MemoryDbSingleton;
import nextstep.Theme;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Repository
public class MemoryReservationRepository implements ReservationRepository {

    private final MemoryDbSingleton memoryDbSingleton = MemoryDbSingleton.getMemoryDbSingleton();
    private final Map<Long, Reservation> reservationMap = memoryDbSingleton.getReservationMap();

    @Override
    public Reservation findById(Long id) {
        return reservationMap.get(id);
    }

    @Override
    public void deleteById(Long id) {
        reservationMap.remove(id);
    }

    @Override
    public Reservation save(LocalDate date, LocalTime time, String name, Theme theme) {
        try {
            Long primaryKey = memoryDbSingleton.getPrimaryKey();
            Reservation reservation = new Reservation(primaryKey, date, time, name, theme);
            reservationMap.put(primaryKey, reservation);
            memoryDbSingleton.increasePrimaryKey();
            return reservation;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
