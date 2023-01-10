package nextstep.main.java.nextstep.repository;

import nextstep.main.java.nextstep.domain.Reservation;
import nextstep.main.java.nextstep.domain.Theme;
import nextstep.main.java.nextstep.exception.exception.NoSuchReservationException;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
@Primary
public class JdbcReservationRepository implements ReservationRepository {
    private static final int EMPTY_SIZE = 0;

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation save(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update((connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setDate(1, Date.valueOf(reservation.getDate()));
            preparedStatement.setTime(2, Time.valueOf(reservation.getTime()));
            preparedStatement.setString(3, reservation.getName());
            preparedStatement.setString(4, reservation.getTheme().getName());
            preparedStatement.setString(5, reservation.getTheme().getDesc());
            preparedStatement.setInt(6, reservation.getTheme().getPrice());
            return preparedStatement;
        }, keyHolder);
        return new Reservation(keyHolder.getKey().longValue(), reservation);
    }

    @Override
    public Optional<Reservation> findOne(Long id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    sql,
                    (rs, count) -> new Reservation(
                            rs.getLong("id"),
                            rs.getDate("date").toLocalDate(),
                            rs.getTime("time").toLocalTime(),
                            rs.getString("name"),
                            new Theme(
                                    rs.getString("theme_name"),
                                    rs.getString("theme_desc"),
                                    rs.getInt("theme_price")
                            )
                    ),
                    id
            ));
        } catch (NoSuchReservationException e) {
            throw new NoSuchReservationException();
        }
    }

    @Override
    public void deleteOne(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        String sql = "SELECT count(*) FROM reservation WHERE date = ? AND time = ?";
        return EMPTY_SIZE != jdbcTemplate.queryForObject(sql, new Object[]{Date.valueOf(date), Time.valueOf(time)}, Integer.class);
    }
}
