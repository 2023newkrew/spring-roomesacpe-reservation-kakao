package nextstep.repository.reservation;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.dto.FindReservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationActorRowMapper = (resultSet, rowNum) ->
            from(resultSet);


    @Override
    public Reservation findById(Long id) {
        return jdbcTemplate.queryForObject(findByIdSql, reservationActorRowMapper, id);
    }

    @Override
    public List<FindReservation> findAll() {
        return jdbcTemplate.query(
                findAllSql,
                (resultSet, rowNum) -> allFrom(resultSet));
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(deleteByIdSql, id);
    }

    @Override
    public void deleteByThemeId(Long themeId) {
        jdbcTemplate.update(deleteByThemeIdSql, themeId);
    }

    @Override
    public Long save(LocalDate date, LocalTime time, String name, Theme theme) {
        validateReservation(date, time);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator preparedStatementCreator = (connection) ->
                getReservationPreparedStatement(connection, date, time, name, theme);

        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return keyHolder.getKey().longValue();
    }

    private void validateReservation(LocalDate date, LocalTime time) {
        Integer count = jdbcTemplate.queryForObject(checkDuplicationSql, Integer.class, date, time);
        if (count > 0) throw new IllegalArgumentException("이미 예약된 일시에는 예약이 불가능합니다.");
    }
}
