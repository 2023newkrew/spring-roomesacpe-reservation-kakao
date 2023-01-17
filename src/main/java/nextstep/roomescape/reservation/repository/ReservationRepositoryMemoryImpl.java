package nextstep.roomescape.reservation.repository;

import nextstep.roomescape.exception.NotExistEntityException;
import nextstep.roomescape.reservation.repository.model.Reservation;
import nextstep.roomescape.exception.DuplicateEntityException;
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
    public Long create(Reservation reservation) {
        if (findByDateTime(reservation.getDate(), reservation.getTime())) {
            throw new DuplicateEntityException("예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있습니다.");
        }
        Long id = reservationCount.getAndIncrement();
        Reservation creatteReservation = new Reservation(id, reservation.getDate(), reservation.getTime(), reservation.getName(), reservation.getTheme());
        reservationList.put(id, creatteReservation);
        return id;
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
    public void delete(long id) {
        if (reservationList.containsKey(id)) {
            reservationList.remove(id);
            return;
        }
        throw new NotExistEntityException("해당 id가 없습니다.");
    }

}
