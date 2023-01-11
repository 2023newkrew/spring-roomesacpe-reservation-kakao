package nextstep.main.java.nextstep.mvc.repository.reservation;

import nextstep.main.java.nextstep.mvc.domain.reservation.Reservation;
import nextstep.main.java.nextstep.mvc.domain.reservation.request.ReservationCreateRequest;
import nextstep.main.java.nextstep.mvc.domain.theme.Theme;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;

@Repository
@Primary
public class JdbcReservationRepository implements ReservationRepository {
    private static final int EMPTY_SIZE = 0;

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("id"),
            resultSet.getDate("date").toLocalDate(),
            resultSet.getTime("time").toLocalTime(),
            resultSet.getString("name"),
            new Theme(
                    resultSet.getLong("theme_id"),
                    resultSet.getString("theme_name"),
                    resultSet.getString("desc"),
                    resultSet.getInt("price")
            )
    );

    @Override
    public Long save(ReservationCreateRequest request) {
        String sql = "INSERT INTO reservation (DATE, TIME, NAME, THEME_ID) " +
                "VALUES (:date, :time, :name, (SELECT id FROM theme WHERE name = :theme_name))";
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(
                sql,
                new MapSqlParameterSource(
                        Map.of(
                        "date", request.getDate(),
                        "time", request.getTime(),
                        "name", request.getName(),
                        "theme_name", request.getThemeName()
                        )
                ),
                generatedKeyHolder
        );
        return generatedKeyHolder.getKeyAs(Long.class);
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        String sql = "SELECT t.name as theme_name, r.*, t.* " +
                "FROM reservation AS r " +
                "JOIN theme AS t ON r.theme_id = t.id " +
                "WHERE r.id = ?";

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, reservationRowMapper, id));
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
