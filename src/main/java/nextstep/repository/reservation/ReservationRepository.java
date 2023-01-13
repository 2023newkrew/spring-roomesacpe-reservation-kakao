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


    String saveSql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price)" +
            "VALUES (?, ?, ?, ?, ?, ?);";
    String checkDuplicationSql = "select count(*) as total_rows from reservation where date = ? and time = ?";

    String createTableSql = "create table reservation (\n" +
            "  id bigint not null auto_increment,\n" +
            "  date date,\n" +
            "  time time,\n" +
            "  name varchar(20),\n" +
            "  theme_name varchar(20),\n" +
            "  theme_desc varchar(255),\n" +
            "  theme_price int,\n" +
            "  primary key (id)\n" +
            ");";
    String dropTableSql = "drop table reservation if exists";

    default PreparedStatement getReservationPreparedStatement(Connection con, LocalDate date, LocalTime time,
                                                              String name, Theme theme) throws SQLException {
        PreparedStatement ps = con.prepareStatement(saveSql, new String[]{"id"});
        ps.setDate(1, Date.valueOf(date));
        ps.setTime(2, Time.valueOf(time));
        ps.setString(3, name);
        ps.setString(4, theme.getName());
        ps.setString(5, theme.getDesc());
        ps.setInt(6, theme.getPrice());
        return ps;
    }

    default Reservation from(ResultSet resultSet) throws SQLException {
        return new Reservation(resultSet.getLong("ID"),
                resultSet.getDate("DATE").toLocalDate(),
                resultSet.getTime("TIME").toLocalTime(),
                resultSet.getString("NAME"),
                new Theme(
                        resultSet.getString("theme_name"),
                        resultSet.getString("theme_desc"),
                        resultSet.getInt("theme_price")
                )
        );
    }

    Reservation findById(Long id);

    void deleteById(Long id) throws SQLException;

    Long save(LocalDate date, LocalTime time, String name, Theme theme);

    void createTable() throws SQLException;

    void dropTable() throws SQLException;
}
