package nextstep.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.domain.Reservation;

public interface ReservationDAO {
    Long insertWithKeyHolder(Reservation reservation);
    Reservation findById(Long id);
    List<Reservation> findByDateAndTime(LocalDate localDate, LocalTime localTime);
    Integer delete(Long id);
}
