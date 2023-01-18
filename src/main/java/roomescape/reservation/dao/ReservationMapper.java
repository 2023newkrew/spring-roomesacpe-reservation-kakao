package roomescape.reservation.dao;

import org.springframework.jdbc.core.RowMapper;
import roomescape.reservation.domain.Reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
public class ReservationMapper implements RowMapper<Reservation> {
    @Override
    public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
        Reservation reservation = new Reservation(
                rs.getLong("id"),
                rs.getDate("date").toLocalDate(),
                rs.getTime("time").toLocalTime(),
                rs.getString("name"),
                rs.getLong("themeid")
//                new Theme(
//                        rs.getString("theme_name"),
//                        rs.getString("theme_desc"),
//                        rs.getInt("theme_price")
//                )
        );
        reservation.setId(rs.getLong("ID"));
        return reservation;
    }
}
