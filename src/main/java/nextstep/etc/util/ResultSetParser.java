package nextstep.etc.util;

import nextstep.reservation.domain.Reservation;
import nextstep.reservation.domain.Theme;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class ResultSetParser {

    public static Boolean existsRow(ResultSet resultSet) throws SQLException {
        return getRows(resultSet) > 0;
    }

    private static int getRows(ResultSet resultSet) throws SQLException {
        resultSet.last();

        return resultSet.getRow();
    }

    public static Reservation parseReservation(ResultSet resultSet) throws SQLException {
        if (getRows(resultSet) == 0) {
            return null;
        }

        Date date = resultSet.getDate("date");
        Time time = resultSet.getTime("time");

        return new Reservation(
                resultSet.getLong("id"),
                date.toLocalDate(),
                time.toLocalTime(),
                resultSet.getString("name"),
                parseTheme(resultSet)
        );
    }

    private static Theme parseTheme(ResultSet resultSet) throws SQLException {
        return new Theme(
                resultSet.getString("theme_name"),
                resultSet.getString("theme_desc"),
                resultSet.getInt("theme_price")
        );
    }

    public static Long parseKey(ResultSet resultSet) throws SQLException {
        resultSet.next();

        return resultSet.getLong(1);
    }
}
