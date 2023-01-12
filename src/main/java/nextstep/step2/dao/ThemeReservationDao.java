package nextstep.step2.dao;

import nextstep.step2.entity.Reservation;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;


@Repository
public interface ThemeReservationDao {
    int insert(Reservation reservation) throws SQLException;
    int deleteReservation(Long id) throws SQLException;
    Reservation findById(Long id) throws SQLException;
}
