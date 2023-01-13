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

    public int countByDateAndTimeAndThemeId(LocalDate date, LocalTime time, Long themeId) {
        String sql = "SELECT count(*) FROM reservation WHERE date = ? and time = ? and theme_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, date, time, themeId);
    }

    public int countByThemeId(Long themeId) {
        String sql = "SELECT count(*) FROM reservation WHERE theme_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, themeId);
    }

    public Optional<Reservation> findById(Long id) {
        String sql = "SELECT *, theme.name AS theme_name, theme.desc AS theme_desc, theme.price AS theme_price " +
                "FROM reservation " +
                "LEFT JOIN theme ON reservation.theme_id = theme.id " +
                "WHERE reservation.id = ?";
        return jdbcTemplate.query(sql, getRowMapper(), id).stream().findAny();
    }

    public void delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
