package nextstep.repository.reservation;

import nextstep.domain.Reservation;
import nextstep.exception.EscapeException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static nextstep.exception.ErrorCode.DUPLICATED_RESERVATION_EXISTS;
import static nextstep.exception.ErrorCode.RESERVATION_NOT_FOUND;

@Repository
public class JdbcReservationRepository extends ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> actorRowMapper =
            (resultSet, rowNum) -> extractReservation(resultSet);

    @Override
    public Long save(LocalDate date, LocalTime time, String name, Long themeId) {
        validateReservation(date, time);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator preparedStatementCreator = (connection) ->
                getReservationPreparedStatement(connection, date, time, name, themeId);

        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return keyHolder.getKey().longValue();
    }

    private void validateReservation(LocalDate date, LocalTime time) {
        String sql = CHECK_DUPLICATION_SQL;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, date, time);
        if (count > 0) {
            throw new EscapeException(DUPLICATED_RESERVATION_EXISTS);
        }
    }

    @Override
    public Long save(Reservation reservation) {
        return this.save(reservation.getDate(), reservation.getTime(),
                reservation.getName(), reservation.getThemeId());
    }

    @Override
    public Reservation findById(Long id) {
        try {
            Reservation reservation = jdbcTemplate.queryForObject(FIND_BY_ID_SQL, actorRowMapper, id);
            return reservation;
        } catch (DataAccessException e) {
            throw new EscapeException(RESERVATION_NOT_FOUND);
        }
    }

    @Override
    public List<Reservation> findByThemeId(Long themeId) {
        List<Reservation> reservations = jdbcTemplate.query(FIND_BY_THEME_ID_SQL, actorRowMapper, themeId);
        return reservations;
    }

    @Override
    public void deleteById(Long id) {
        int updatedRows = jdbcTemplate.update(DELETE_BY_ID_SQL, id);
        if (updatedRows == 0) {
            throw new EscapeException(RESERVATION_NOT_FOUND);
        }
    }

    @Override
    public void createTable() {
        jdbcTemplate.execute(CREATE_TABLE_SQL);
    }

    @Override
    public void dropTable() {
        jdbcTemplate.execute(DROP_TABLE_SQL);
    }
}
