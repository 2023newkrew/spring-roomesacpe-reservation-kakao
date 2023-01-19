package nextstep.dao;

import nextstep.entity.Reservation;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;


public interface ThemeReservationDao {

    int insert(Reservation reservation) throws SQLException;

    int deleteReservation(Long id) throws SQLException;

    Reservation findById(Long id) throws SQLException;
}
