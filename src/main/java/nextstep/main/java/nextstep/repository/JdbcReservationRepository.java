package nextstep.main.java.nextstep.repository;

import nextstep.main.java.nextstep.domain.Reservation;
import nextstep.main.java.nextstep.repositoryUtil.CustomPreparedStatementCreator;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class JdbcReservationRepository implements ReservationRepository {
    private static final int EMPTY_SIZE = 0;
    private static final int ONE = 1;

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation save(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((connection) ->
                        CustomPreparedStatementCreator.insertReservationPreparedStatement(connection, reservation)
                , keyHolder);
        return new Reservation(keyHolder.getKey()
                .longValue(), reservation);
    }

    @Override
    public Optional<Reservation> findOne(Long id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    sql,
                    (rs, count) -> Reservation.of(rs),
                    id
            ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Reservation> findAllByThemeId(Long themeId) {
        String sql = "SELECT * FROM reservation WHERE theme_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> Reservation.of(rs), themeId);
    }

    @Override
    public Boolean deleteOne(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return ONE == jdbcTemplate.update(sql, id);
    }

    @Override
    public Boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        String sql = "SELECT count(*) FROM reservation WHERE date = ? AND time = ?";
        return EMPTY_SIZE != jdbcTemplate.queryForObject(sql, new Object[]{Date.valueOf(date), Time.valueOf(time)}, Integer.class);
    }
}
