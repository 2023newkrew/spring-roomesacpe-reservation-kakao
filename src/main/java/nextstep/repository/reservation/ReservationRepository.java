package nextstep.repository.reservation;

import nextstep.domain.Reservation;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public abstract class ReservationRepository {

    protected String FIND_BY_ID_SQL = "select * from reservation where id = ?";
    protected String FIND_BY_THEME_ID_SQL = "select * from reservation where theme_id = ?";
    protected String DELETE_BY_ID_SQL = "delete from reservation where id = ?";
    protected String SAVE_SQL = "INSERT INTO reservation (date, time, name, theme_id)" +
            "VALUES (?, ?, ?, ?);";
    protected String CHECK_DUPLICATION_SQL = "select count(*) as total_rows from reservation where date = ? and time = ?";
    protected String CREATE_TABLE_SQL = "create table reservation (\n" +
            "  id bigint not null auto_increment,\n" +
            "  date date,\n" +
            "  time time,\n" +
            "  name varchar(20),\n" +
            "  theme_id bigint,\n" +
            "  primary key (id)\n" +
            ");";
    protected String DROP_TABLE_SQL = "drop table reservation if exists";

    protected PreparedStatement getReservationPreparedStatement(Connection con, LocalDate date, LocalTime time,
                                                                String name, Long themeId) throws SQLException {
        PreparedStatement ps = con.prepareStatement(SAVE_SQL, new String[]{"id"});
        ps.setDate(1, Date.valueOf(date));
        ps.setTime(2, Time.valueOf(time));
        ps.setString(3, name);
        ps.setLong(4, themeId);
        return ps;
    }

    public abstract Long save(LocalDate date, LocalTime time, String name, Long themeId);

    public abstract Long save(Reservation reservation);

    public abstract Reservation findById(Long id);

    public abstract List<Reservation> findByThemeId(Long id);

    public abstract void deleteById(Long id);

    public abstract void createTable();

    public abstract void dropTable();

    public Reservation extractReservation(ResultSet resultSet) throws SQLException {
        return new Reservation(resultSet.getLong("ID"),
                resultSet.getDate("DATE").toLocalDate(),
                resultSet.getTime("TIME").toLocalTime(),
                resultSet.getString("NAME"),
                resultSet.getLong("THEME_ID")
        );
    }
}
