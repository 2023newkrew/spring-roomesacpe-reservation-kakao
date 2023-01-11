package nextstep.repository;

import nextstep.domain.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public class ReservationJdbcTemplateDao implements ReservationDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationJdbcTemplateDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(getPreparedStatementCreatorForSave(reservation), keyHolder);
        return keyHolder.getKey().longValue();
    }

    public int countByDateAndTime(LocalDate date, LocalTime time) {
        String sql = "SELECT count(*) FROM reservation WHERE date = ? and time = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, date, time);
    }

    public Optional<Reservation> findById(Long id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        return jdbcTemplate.query(sql, getRowMapper(), id).stream().findAny();
    }

    public void clear() {
        String sql = "DELETE FROM reservation";
        jdbcTemplate.update(sql);
        resetId();
    }

    private void resetId() {
        String sql = "ALTER TABLE reservation ALTER COLUMN id RESTART WITH 1";
        jdbcTemplate.execute(sql);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
