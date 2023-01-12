package nextstep.etc.util;

import nextstep.reservation.dto.ReservationDTO;
import nextstep.reservation.dto.ThemeDTO;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class ResultSetParser {

    public static Boolean existsRow(ResultSet resultSet) throws SQLException {
        return resultSet.getRow() != 0;
    }

    public static ReservationDTO parseReservationDto(ResultSet resultSet) throws SQLException {
        if (resultSet.getRow() == 0) {
            return null;
        }

        Date date = resultSet.getDate("date");
        Time time = resultSet.getTime("time");

        return new ReservationDTO(
                resultSet.getLong("id"),
                date.toLocalDate(),
                time.toLocalTime(),
                resultSet.getString("name"),
                parseThemeDto(resultSet)
        );
    }

    private static ThemeDTO parseThemeDto(ResultSet resultSet) throws SQLException {
        return new ThemeDTO(
                resultSet.getString("theme_name"),
                resultSet.getString("theme_desc"),
                resultSet.getInt("theme_price")
        );
    }
}
