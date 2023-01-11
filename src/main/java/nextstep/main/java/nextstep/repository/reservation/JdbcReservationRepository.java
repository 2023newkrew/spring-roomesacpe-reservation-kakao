package nextstep.main.java.nextstep.repository.reservation;

import nextstep.main.java.nextstep.domain.reservation.Reservation;
import nextstep.main.java.nextstep.domain.reservation.ReservationCreateRequestDto;
import nextstep.main.java.nextstep.domain.theme.Theme;
import nextstep.main.java.nextstep.global.exception.exception.NoSuchReservationException;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
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

    @Override
    public Long save(ReservationCreateRequestDto request) {
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
    public Optional<Reservation> findOne(Long id) {
        return Optional.empty();
//        String sql = "SELECT * FROM reservation WHERE id = ?";
//        try {
//            return Optional.ofNullable(jdbcTemplate.queryForObject(
//                    sql,
//                    (rs, count) -> new Reservation(
//                            rs.getLong("id"),
//                            rs.getDate("date").toLocalDate(),
//                            rs.getTime("time").toLocalTime(),
//                            rs.getString("name"),
//                            new Theme(
//                                    rs.getString("theme_name"),
//                                    rs.getString("theme_desc"),
//                                    rs.getInt("theme_price")
//                            )
//                    ),
//                    id
//            ));
//        } catch (EmptyResultDataAccessException e) {
//            throw new NoSuchReservationException();
//        }
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
