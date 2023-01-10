package kakao.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import kakao.domain.Reservation;

public interface ReservationRepository {

    public long save(Reservation reservation);

    public Reservation findById(Long id);

    public List<Reservation> findByDateAndTime(LocalDate date, LocalTime time);

    public int delete(Long id);
}
