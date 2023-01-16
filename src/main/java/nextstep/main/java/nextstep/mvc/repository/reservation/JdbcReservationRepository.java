package nextstep.main.java.nextstep.mvc.repository.reservation;

import nextstep.main.java.nextstep.global.exception.exception.NotSupportedOperationException;
import nextstep.main.java.nextstep.mvc.domain.reservation.Reservation;
import nextstep.main.java.nextstep.mvc.domain.reservation.request.ReservationCreateRequest;
import nextstep.main.java.nextstep.mvc.domain.theme.Theme;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcReservationRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> Reservation.builder()
            .id(resultSet.getLong("id"))
            .date(resultSet.getDate("date").toLocalDate())
            .time(resultSet.getTime("time").toLocalTime())
            .name(resultSet.getString("name"))
            .theme(new Theme(
                    resultSet.getLong("theme_id"),
                    resultSet.getString("theme_name"),
                    resultSet.getString("desc"),
                    resultSet.getInt("price")
            )).build();

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
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, reservationRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Reservation> findAll() {
        throw new NotSupportedOperationException();
    }

    @Override
    public void update(Long id, ReservationCreateRequest request) {
        throw new NotSupportedOperationException();
    }

    @Override
    public Boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        String sql = "SELECT EXISTS(SELECT * FROM reservation WHERE date = ? AND time = ?)";
        return jdbcTemplate.queryForObject(sql ,new Object[] {date, time}, Boolean.class);
    }

    @Override
    public Boolean existsById(Long id) {
        String sql = "SELECT EXISTS(SELECT * FROM reservation WHERE id = ?)";
        return jdbcTemplate.queryForObject(sql ,new Object[] {id}, Boolean.class);
    }

    @Override
    public Boolean existsByThemeId(Long themeId) {
        String sql = "SELECT EXISTS(SELECT * FROM reservation WHERE theme_id = ?)";
        return jdbcTemplate.queryForObject(sql ,new Object[] {themeId}, Boolean.class);
    }
}
