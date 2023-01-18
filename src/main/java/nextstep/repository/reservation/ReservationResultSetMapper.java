package nextstep.repository.reservation;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ReservationResultSetMapper {
    public static Reservation mapRow(ResultSet rs) throws SQLException {
        return Reservation.of(
                rs.getLong("id"),
                rs.getDate("date").toLocalDate(),
                rs.getTime("time").toLocalTime(),
                rs.getString("name"),
                Theme.of(
                        rs.getLong("theme.id"),
                        rs.getString("theme.name"),
                        rs.getString("theme.desc"),
                        rs.getInt("theme.price")
                )
        );
    }
}
