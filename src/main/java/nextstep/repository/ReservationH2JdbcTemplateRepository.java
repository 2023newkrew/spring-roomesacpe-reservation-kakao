package nextstep.repository;

import nextstep.domain.Reservation;
import nextstep.exception.ReservationNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Qualifier("jdbcTemplate")
@Repository
public class ReservationH2JdbcTemplateRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationH2JdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation add(Reservation reservation) {
        String sql = "INSERT INTO reservation (date, time, name, theme_id) VALUES (?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setLong(4, reservation.getTheme().getId());
            return ps;
        }, keyHolder);

        reservation.setId(keyHolder.getKey().longValue());
        return reservation;
    }

    @Override
    public Reservation get(Long id)  throws ReservationNotFoundException {
        String sql = "SELECT r.*, t.* FROM reservation r JOIN theme t ON r.theme_id = t.id where r.id = ?";
        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    (resultSet, rowNum) -> ReservationResultSetMapper.mapRow(resultSet),
                    id);
        } catch (EmptyResultDataAccessException e) {
            throw new ReservationNotFoundException();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean hasReservationAt(LocalDate date, LocalTime time) {
        String sql = "SELECT id AS cnt FROM reservation WHERE date = ? AND time = ? LIMIT 1";
        List<Long> reservationIds = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> rs.getLong("id"),
                Date.valueOf(date),
                Time.valueOf(time)
        );

        return reservationIds.size() == 1;
    }
}
