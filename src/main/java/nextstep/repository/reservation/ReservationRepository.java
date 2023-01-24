package nextstep.repository.reservation;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.dto.FindReservation;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository {

    String findByIdSql = "select * from reservation where id = ?";
    String findAllSql = "select reservation.id, reservation.date, reservation.time, reservation.name, theme.name, theme.desc, theme.price " +
            "from reservation, theme " +
            "where reservation.theme_id = theme.id";
    String deleteByIdSql = "delete from reservation where id = ?";
    String deleteByThemeIdSql = "delete from reservation where theme_id = ?";
    String saveSql = "insert into reservation (date, time, name, theme_id) " +
            "values (?, ?, ?, ?);";
    String checkDuplicationSql = "select count(*) as total_rows from reservation where date = ? and time = ?";


    default PreparedStatement getReservationPreparedStatement(Connection con, LocalDate date, LocalTime time,
                                                              String name, Theme theme) throws SQLException {
        PreparedStatement ps = con.prepareStatement(saveSql, new String[]{"id"});
        ps.setDate(1, Date.valueOf(date));
        ps.setTime(2, Time.valueOf(time));
        ps.setString(3, name);
        ps.setLong(4, theme.getId());
        return ps;
    }

    default Reservation from(ResultSet resultSet) throws SQLException {
        return new Reservation(resultSet.getLong("ID"),
                resultSet.getDate("DATE").toLocalDate(),
                resultSet.getTime("TIME").toLocalTime(),
                resultSet.getString("NAME"),
                resultSet.getLong("THEME_ID")
        );
    }

    default FindReservation allFrom(ResultSet resultSet) throws SQLException {
        return new FindReservation(resultSet.getLong("ID"),
                resultSet.getDate("DATE").toLocalDate(),
                resultSet.getTime("TIME").toLocalTime(),
                resultSet.getString("NAME"),
                resultSet.getString("NAME"),
                resultSet.getString("DESC"),
                resultSet.getInt("PRICE")
        );
    }

    Reservation findById(Long id);

    List<FindReservation> findAll();

    void deleteById(Long id);

    void deleteByThemeId(Long themeId);

    Long save(LocalDate date, LocalTime time, String name, Theme theme);
}
