package roomescape.repository.mapper;

import roomescape.domain.Reservation;
import roomescape.domain.Theme;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationMapper {
    private ReservationMapper() {
    }

    public static Reservation mapToReservation(ResultSet resultSet) throws SQLException {
        return new Reservation(
                resultSet.getLong("id"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime(),
                resultSet.getString("name"),
                new Theme(
                        resultSet.getLong("theme.id"),
                        resultSet.getString("theme.name"),
                        resultSet.getString("theme.desc"),
                        resultSet.getInt("theme.price")
                )
        );
    }
}
