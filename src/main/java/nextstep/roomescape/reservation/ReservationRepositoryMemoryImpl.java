package nextstep.roomescape.reservation;

import nextstep.roomescape.reservation.domain.Reservation;
import nextstep.roomescape.reservation.exception.CreateReservationException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


public class ReservationRepositoryMemoryImpl implements ReservationRepository{
    private static final Map<Long, Reservation> reservationList = new HashMap<>();
    private static final AtomicLong reservationCount = new AtomicLong(1);
    @Override
    public Reservation create(Reservation reservation) {
        if (findByDateTime(reservation.getDate(), reservation.getTime())) {
            throw new CreateReservationException();
        }
        Long id = reservationCount.getAndIncrement();
        Reservation creatteReservation = new Reservation(id, reservation.getDate(), reservation.getTime(), reservation.getName(), reservation.getTheme());
        reservationList.put(id, creatteReservation);
        return creatteReservation;
    }

    @Override
    public Reservation findById(long id) {
        return reservationList.getOrDefault(id, null);
    }
    @Override
    public Boolean findByDateTime(LocalDate date, LocalTime time) {
        return reservationList
                .values()
                .stream()
                .anyMatch(reservation -> reservation.getDate().equals(date) && reservation.getTime().equals(time));
    }

    @Override
    @Transactional
    public Boolean delete(long id) {
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
