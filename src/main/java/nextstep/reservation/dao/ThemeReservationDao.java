package nextstep.reservation.dao;

import nextstep.reservation.entity.Reservation;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;


@Repository
public interface ThemeReservationDao {
    int insert(Reservation reservation) throws SQLException;
    int delete(Long id) throws SQLException;
    Reservation findById(Long id) throws SQLException;
}
