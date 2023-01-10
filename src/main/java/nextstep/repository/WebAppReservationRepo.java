package nextstep.repository;

import nextstep.Theme;
import nextstep.domain.reservation.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.ZoneId;

@Repository
public class WebAppReservationRepo implements ReservationRepo{

    private JdbcTemplate jdbcTemplate;

    public WebAppReservationRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation findById(long id) {
        String sql = "SELECT id, date, time, name, theme_name, theme_desc, theme_price FROM reservation WHERE id = ?;";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Reservation(
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
            return null;
        }
    }

    public long add(Reservation reservation) {
        String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con-> {
            PreparedStatement ps = con.prepareStatement(sql, new String[] {"id"});
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
}
