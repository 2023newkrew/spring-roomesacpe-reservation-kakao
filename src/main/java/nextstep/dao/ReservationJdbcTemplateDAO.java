package nextstep.dao;

import nextstep.domain.Reservation;
import nextstep.domain.ReservationSaveForm;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationJdbcTemplateDAO implements ReservationDAO {
    private final JdbcTemplate jdbcTemplate;

    public ReservationJdbcTemplateDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(ReservationSaveForm reservationSaveForm) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator insertPreparedStatementCreator = ReservationDAO.getInsertPreparedStatementCreator(reservationSaveForm);

        jdbcTemplate.update(insertPreparedStatementCreator, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(FIND_BY_ID_SQL, RESERVATION_ROW_MAPPER, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Reservation> findByDateAndTimeAndThemeId(LocalDate date, LocalTime time, Long themeId) {
        return jdbcTemplate.query(FIND_BY_DATE_TIME_THEME_SQL, RESERVATION_ROW_MAPPER, date, time, themeId);
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update(DELETE_BY_ID_SQL, id);
    }

    @Override
    public boolean existsByThemeId(Long themeId) {
        return jdbcTemplate.queryForObject(COUNT_BY_THEME_ID_SQL, Integer.class, themeId) > 0;
    }
}
