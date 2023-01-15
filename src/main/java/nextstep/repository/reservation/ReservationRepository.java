package nextstep.repository.reservation;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.repository.theme.ThemeRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public interface ReservationRepository {

    String findByIdSql = "select * from reservation where id = ?";
    String deleteByIdSql = "delete from reservation where id = ?";
    String deleteByThemeIdSql = "delete from reservation where theme_id = ?";
    String saveSql = "INSERT INTO reservation (date, time, name, theme_id)" +
            "VALUES (?, ?, ?, ?);";
    String checkDuplicationSql = "select count(*) as total_rows from reservation where date = ? and time = ?";

    String createTableSql = "create table reservation (\n" +
            "  id bigint not null auto_increment,\n" +
            "  date date,\n" +
            "  time time,\n" +
            "  name varchar(20),\n" +
            "  theme_id bigint,\n" +
            "  primary key (id)\n" +
            ");";
    String dropTableSql = "drop table reservation if exists";

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

    Reservation findById(Long id);
    void deleteById(Long id);
    void deleteByThemeId(Long themeId);
    Long save(LocalDate date, LocalTime time, String name, Theme theme);
    void createTable() throws SQLException;
    void dropTable() throws SQLException;

}
