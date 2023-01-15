package nextstep.reservation.dao;

import nextstep.reservation.entity.Reservation;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;


@Repository
public interface ThemeReservationDao {
    int insert(Reservation reservation);
    int delete(Long id);
    Optional<Reservation> findById(Long id);

    Optional<Reservation> findByDatetime(LocalDate date, LocalTime time);
}
