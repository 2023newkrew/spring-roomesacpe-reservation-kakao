package nextstep.dao;

import nextstep.domain.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationJdbcTemplateDAO implements ReservationDAO {
    private final JdbcTemplate jdbcTemplate;

    public ReservationJdbcTemplateDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator insertPreparedStatementCreator = ReservationDAO.getInsertPreparedStatementCreator(reservation);

        jdbcTemplate.update(insertPreparedStatementCreator, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Reservation findById(Long id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_SQL, RESERVATION_ROW_MAPPER, id);
    }

    @Override
    public List<Reservation> findByDateAndTime(LocalDate date, LocalTime time) {
        return jdbcTemplate.query(FIND_BY_DATE_TIME_SQL, RESERVATION_ROW_MAPPER, date, time);
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update(DELETE_BY_ID_SQL, id);
    }
}
