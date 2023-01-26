package roomescape.reservation.repository;

import roomescape.domain.Reservation;

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
                resultSet.getLong("theme_id")
        );
    }
}