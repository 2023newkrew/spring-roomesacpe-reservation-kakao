package nextstep.repository;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> actorRowMapper = (resultSet, rowNum) -> {
        return Reservation.from(resultSet);
    };

    @Override
    public Reservation findById(Long id) {
        return jdbcTemplate.queryForObject(findByIdSql, actorRowMapper, id);
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(deleteByIdSql, id);
    }

    @Override
    public Long save(LocalDate date, LocalTime time, String name, Theme theme) {
        validateReservation(date, time);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            return getReservationPreparedStatement(connection, date, time, name, theme);
        };

        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void createTable() throws SQLException {
        jdbcTemplate.execute(createTableSql);
    }

    @Override
    public void dropTable() throws SQLException {
        jdbcTemplate.execute(dropTableSql);
    }

    private void validateReservation(LocalDate date, LocalTime time) {
        String sql = checkDuplicationSql;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, date, time);
        if (count > 0) throw new IllegalArgumentException("이미 예약된 일시에는 예약이 불가능합니다.");
    }
}
