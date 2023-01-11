package nextstep.repository;

import nextstep.MemoryDbSingleton;
import nextstep.Reservation;
import nextstep.Theme;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Map;

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
    public Long save(LocalDate date, LocalTime time, String name, Theme theme) {
        validateReservation(date, time);
        Long primaryKey = memoryDbSingleton.getPrimaryKey();
        Reservation reservation = new Reservation(primaryKey, date, time, name, theme);
        reservationMap.put(primaryKey, reservation);
        memoryDbSingleton.increasePrimaryKey();
        return primaryKey;
    }

    private void validateReservation(LocalDate date, LocalTime time) {
        Collection<Reservation> reservations = reservationMap.values();

        // 같은 날짜와 시간을 가진 예약 갯수 반환
        long count = reservations.stream()
                .filter(reservation -> reservation.getDate().equals(date) && reservation.getTime().equals(time))
                .count();

        if (count > 0) throw new IllegalArgumentException("이미 예약된 일시에는 예약이 불가능합니다.");
    }
}
