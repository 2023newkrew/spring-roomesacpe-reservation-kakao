package nextstep.repository.reservation;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public abstract class ReservationRepository {

    protected String FIND_BY_ID_SQL = "select * from reservation where id = ?";
    protected String DELETE_BY_ID_SQL = "delete from reservation where id = ?";
    protected String SAVE_SQL = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price)" +
            "VALUES (?, ?, ?, ?, ?, ?);";
    protected String CHECK_DUPLICATION_SQL = "select count(*) as total_rows from reservation where date = ? and time = ?";
    protected String CREATE_TABLE_SQL = "create table reservation (\n" +
            "  id bigint not null auto_increment,\n" +
            "  date date,\n" +
            "  time time,\n" +
            "  name varchar(20),\n" +
            "  theme_name varchar(20),\n" +
            "  theme_desc varchar(255),\n" +
            "  theme_price int,\n" +
            "  primary key (id)\n" +
            ");";
    protected String DROP_TABLE_SQL = "drop table reservation if exists";

    protected PreparedStatement getReservationPreparedStatement(Connection con, LocalDate date, LocalTime time,
                                                              String name, Theme theme) throws SQLException {
        PreparedStatement ps = con.prepareStatement(SAVE_SQL, new String[]{"id"});
        ps.setDate(1, Date.valueOf(date));
        ps.setTime(2, Time.valueOf(time));
        ps.setString(3, name);
        ps.setString(4, theme.getName());
        ps.setString(5, theme.getDesc());
        ps.setInt(6, theme.getPrice());
        return ps;
    }

    public abstract Long save(LocalDate date, LocalTime time, String name, Theme theme);

    public abstract Long save(Reservation reservation);

    public abstract Reservation findById(Long id);

    public abstract void deleteById(Long id);

    public abstract void createTable();

    public abstract void dropTable();
}
