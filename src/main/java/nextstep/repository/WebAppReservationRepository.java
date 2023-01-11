package nextstep.repository;

import nextstep.domain.theme.Theme;
import nextstep.domain.reservation.Reservation;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.Optional;

@Repository
public class WebAppReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public WebAppReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Reservation> findById(long id) {
        Reservation reservation = null;
        String sql = "SELECT id, date, time, name, theme_name, theme_desc, theme_price FROM reservation WHERE id = ?;";
        try {
            reservation = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Reservation(
                    rs.getLong("id"),
                    rs.getDate("date").toLocalDate(),
                    rs.getTime("time").toLocalTime(),
                    rs.getString("name"),
                    new Theme(
                            rs.getString("theme_name"),
                            rs.getString("theme_desc"),
                            rs.getInt("theme_price")
                    )
            ), id);
        } catch (IncorrectResultSizeDataAccessException ex) {
        }
        return Optional.ofNullable(reservation);
    }

    public long add(Reservation reservation) {
        String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setString(4, reservation.getTheme().getName());
            ps.setString(5, reservation.getTheme().getDesc());
            ps.setInt(6, reservation.getTheme().getPrice());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public int delete(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public int countByDateAndTime(Date date, Time time) {
        String sql = "SELECT COUNT(*) FROM reservation WHERE date = ? AND time = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, date, time);
    }

    public void deleteAll() {
//        String sql = "TRUNCATE TABLE reservation;";
        String sql = "DROP TABLE reservation;";
        sql += "CREATE TABLE RESERVATION\n" +
                "(\n" +
                "    id          bigint not null auto_increment,\n" +
                "    date        date,\n" +
                "    time        time,\n" +
                "    name        varchar(20),\n" +
                "    theme_name  varchar(20),\n" +
                "    theme_desc  varchar(255),\n" +
                "    theme_price int,\n" +
                "    primary key (id)\n" +
                ");";
        jdbcTemplate.update(sql);
    }

}