package roomescape.dao.reservation.preparedstatementcreator;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import org.springframework.jdbc.core.PreparedStatementCreator;
import roomescape.dto.Reservation;

public class InsertReservationPreparedStatementCreator implements PreparedStatementCreator {

    private static final String SQL =
            "INSERT INTO reservation(date, time, name, theme_id) "
                    + "VALUES (?, ?, ?, ?);";

    private final Reservation reservation;

    public InsertReservationPreparedStatementCreator(Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement(SQL, new String[]{"id"});
        ps.setDate(1, Date.valueOf(reservation.getDate()));
        ps.setTime(2, Time.valueOf(reservation.getTime()));
        ps.setString(3, reservation.getName());
        ps.setLong(4, reservation.getThemeId());
        return ps;
    }
}
