package nextstep.reservation.dao;

import nextstep.reservation.entity.Reservation;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ThemeReservationDao {
    int insert(Reservation reservation);
    int delete(Long id);
    Optional<Reservation> findById(Long id);
}
