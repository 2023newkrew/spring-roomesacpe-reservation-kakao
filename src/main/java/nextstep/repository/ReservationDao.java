package nextstep.repository;

import nextstep.domain.Reservation;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface ReservationDao {
    Long save(Reservation reservation);

    int countByDateAndTime(LocalDate date, LocalTime time);

    Reservation findById(Long id);

    void delete(Long id);
}
