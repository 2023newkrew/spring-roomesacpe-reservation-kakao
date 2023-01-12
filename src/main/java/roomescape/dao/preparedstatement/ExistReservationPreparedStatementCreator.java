package roomescape.dao.preparedstatement;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.jdbc.core.PreparedStatementCreator;

public class ExistReservationPreparedStatementCreator implements PreparedStatementCreator {

    String SQL =
            "SELECT EXISTS ( SELECT * FROM reservation WHERE date = ? and time = ?) as result;";

    private final Date date;
    private final Time time;

    public ExistReservationPreparedStatementCreator(LocalDate date, LocalTime time) {
        this(Date.valueOf(date), Time.valueOf(time));
    }

    public ExistReservationPreparedStatementCreator(Date date, Time time) {
        this.date = date;
        this.time = time;
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement(SQL, new String[]{"id"});
        ps.setDate(1, date);
        ps.setTime(2, time);
        return ps;
    }
}
