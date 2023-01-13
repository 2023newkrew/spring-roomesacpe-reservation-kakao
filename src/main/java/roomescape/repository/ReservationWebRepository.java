package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Theme;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public class ReservationWebRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("id"),
            resultSet.getDate("date").toLocalDate(),
            resultSet.getTime("time").toLocalTime(),
            resultSet.getString("name"),
            new Theme(
                    resultSet.getLong("theme.id"),
                    resultSet.getString("theme.name"),
                    resultSet.getString("theme.desc"),
                    resultSet.getInt("theme.price")
            )
    );

    public ReservationWebRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long insertReservation(Reservation reservation) {
        String sql = "INSERT INTO reservation (date, time, name, theme_id) VALUES (?, ?, ?, ?)";
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
    public Optional<Reservation> getReservation(Long id) {
        String sql = "SELECT * FROM reservation " +
                "JOIN theme ON reservation.theme_id = theme.id " +
                "WHERE reservation.id = (?)";
        return jdbcTemplate.query(sql, reservationRowMapper, id)
                .stream()
                .findAny();
    }

    @Override
    public void deleteReservation(Long id) {
        String sql = "DELETE FROM reservation WHERE id = (?)";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Reservation> getReservationByDateAndTime(LocalDate date, LocalTime time) {
        String sql = "SELECT * FROM reservation " +
                "JOIN theme ON reservation.theme_id = theme.id " +
                "WHERE date = (?) and time = (?)";
        return jdbcTemplate.query(sql, reservationRowMapper, date, time)
                .stream()
                .findAny();
    }

    @Override
    public void deleteAllReservations() {
        String sql = "DELETE FROM reservation";
        jdbcTemplate.execute(sql);
    }

    @Override
    public void deleteReservationByThemeId(Long themeId) {
        String sql = "DELETE FROM reservation WHERE theme_id = (?)";
        jdbcTemplate.update(sql, themeId);
    }
}
