package nextstep.repository.reservation;

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

    @Override
    public Optional<Reservation> findById(long id) {
        Reservation reservation = null;
        String sql = "SELECT " +
                " r.id," +
                " r.date," +
                " r.time," +
                " r.name," +
                " t.id theme_id," +
                " t.name theme_name," +
                " t.desc theme_desc," +
                " t.price theme_price" +
                " FROM reservation r" +
                " INNER JOIN theme t ON r.theme_id = t.id" +
                " WHERE r.id = ?";
        try {
            reservation = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Reservation(
                    rs.getLong("id"),
                    rs.getDate("date").toLocalDate(),
                    rs.getTime("time").toLocalTime(),
                    rs.getString("name"),
                    new Theme(
                            rs.getLong("theme_id"),
                            rs.getString("theme_name"),
                            rs.getString("theme_desc"),
                            rs.getInt("theme_price")
                    )
            ), id);
        } catch (IncorrectResultSizeDataAccessException ex) {
            ex.printStackTrace();
        }
        return Optional.ofNullable(reservation);
    }

    @Override
    public long add(Reservation reservation) {
        String sql = "INSERT INTO reservation (date, time, name, theme_id) VALUES (?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setLong(4, reservation.getTheme().getId());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public int delete(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int countByDateAndTime(Date date, Time time) {
        String sql = "SELECT COUNT(*) FROM reservation WHERE date = ? AND time = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, date, time);
    }

}
