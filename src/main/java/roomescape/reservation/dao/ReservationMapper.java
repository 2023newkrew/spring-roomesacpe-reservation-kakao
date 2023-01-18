package roomescape.reservation.dao;

import org.springframework.jdbc.core.RowMapper;
import roomescape.reservation.domain.Reservation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationMapper implements RowMapper<Reservation> {
    @Override
    public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Reservation(
                rs.getLong("id"),
                rs.getDate("date").toLocalDate(),
                rs.getTime("time").toLocalTime(),
                rs.getString("name"),
                rs.getLong("themeid")
        );
    }
}
