package nextstep.web;

import nextstep.model.Reservation;
import nextstep.repository.ReservationConverter;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public class JdbcTemplateReservationRepository implements nextstep.repository.ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation save(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatementCreator creator = con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ReservationConverter.set(ps, reservation);
            return ps;
        };

        jdbcTemplate.update(creator, keyHolder);
        Long id = keyHolder.getKey().longValue();

        return new Reservation(id, reservation.getDate(), reservation.getTime(), reservation.getName(), reservation.getTheme());
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        try {
            String sql = "SELECT id, date, time, name, theme_name, theme_desc, theme_price FROM reservation WHERE id = ?";
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, (rs, rowNum) -> ReservationConverter.get(rs, rs.getLong("id")), id));
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
    public Boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        String sql = "SELECT count(*) FROM reservation WHERE date=? AND time=?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, date, time);
        return  count > 0;
    }

    public void deleteAll() {
        String sql = "DELETE FROM reservation";
        jdbcTemplate.update(sql);
    }
}
