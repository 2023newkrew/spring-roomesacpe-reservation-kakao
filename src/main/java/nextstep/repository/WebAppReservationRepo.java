package nextstep.repository;

import nextstep.domain.reservation.Reservation;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;

@Repository
public class WebAppReservationRepo implements ReservationRepo {

    private JdbcTemplate jdbcTemplate;

    public WebAppReservationRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation findById(long id) {
        String sql = "SELECT id, date, time, name, theme_id FROM reservation WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Reservation(
                    rs.getLong("id"),
                    rs.getDate("date").toLocalDate(),
                    rs.getTime("time").toLocalTime(),
                    rs.getString("name"),
                    rs.getLong("theme_id")
            ), id);
        } catch (IncorrectResultSizeDataAccessException ex) {
            return null;
        }
    }

    public long save(Reservation reservation) {
        String sql = "INSERT INTO reservation (date, time, name, theme_id) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setLong(4, reservation.getThemeId());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public int delete(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public int findByDateAndTimeAndTheme(Date date, Time time, long themeId) {
        String sql = "SELECT COUNT(*) FROM reservation WHERE date = ? AND time = ? AND theme_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, date, time, themeId);
    }
}
