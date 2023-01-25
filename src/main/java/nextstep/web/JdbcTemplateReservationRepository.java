package nextstep.web;

import nextstep.model.Reservation;
import nextstep.repository.ReservationSQL;
import nextstep.util.JdbcRemoveDuplicateUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTemplateReservationRepository implements nextstep.repository.ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation save(Reservation reservation) {
        String sql = ReservationSQL.INSERT.toString();
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            JdbcRemoveDuplicateUtils.setReservationToStatement(ps, reservation);
            return ps;
        }, keyHolder);
        Long id = keyHolder.getKeyAs(Long.class);

        return new Reservation(id, reservation.getDate(), reservation.getTime(), reservation.getName(), reservation.getThemeId());
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        try {
            String sql = ReservationSQL.SELECT_BY_ID.toString();
            RowMapper<Reservation> rowMapper = (rs, rowNum) -> JdbcRemoveDuplicateUtils.getReservationFromResultSet(rs, rs.getLong("id"));
            return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Reservation> findByThemeId(Long themeId) {
        String sql = ReservationSQL.SELECT_BY_THEME_ID.toString();
        RowMapper<Reservation> rowMapper = (rs, rowNum) -> JdbcRemoveDuplicateUtils.getReservationFromResultSet(rs, rs.getLong("id"));
        return jdbcTemplate.query(sql, rowMapper, themeId);
    }

    @Override
    public void deleteById(Long id) {
        String sql = ReservationSQL.DELETE_BY_ID.toString();
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        String sql = ReservationSQL.COUNT_BY_DATE_AND_TIME.toString();
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, date, time);
        return  count > 0;
    }
}