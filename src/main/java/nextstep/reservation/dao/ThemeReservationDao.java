package nextstep.reservation.dao;

import nextstep.reservation.entity.Reservation;
import org.springframework.stereotype.Repository;


@Repository
public interface ThemeReservationDao {
    int insert(Reservation reservation);
    int delete(Long id);
    Reservation findById(Long id);
}
