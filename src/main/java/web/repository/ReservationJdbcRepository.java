package web.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import web.domain.Reservation;
import web.dto.response.ReservationIdDto;
import web.exception.NoSuchReservationException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Long> findAllReservationWithDateAndTime(Reservation reservation) {

        String selectSql = "SELECT id FROM reservation WHERE date = (?) AND time = (?) AND theme_id = (?) LIMIT 1 ";

        return jdbcTemplate.query(selectSql, ((rs, rowNum) ->
                rs.getLong("id")),
                Date.valueOf(reservation.getDate()),
                Time.valueOf(reservation.getTime()),
                reservation.getThemeId()
                );
    }

    public ReservationIdDto createReservation(Reservation reservation) {
        String sql = "INSERT INTO reservation (date, time, name, theme_id) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setLong(4, reservation.getThemeId());
            return ps;
        }, keyHolder);

        return new ReservationIdDto((Long) keyHolder.getKey());
    }

    public Reservation findReservationById(Long id) throws NoSuchReservationException {
        String sql = "SELECT * FROM reservation WHERE id = (?) LIMIT 1 ";

        List<Reservation> reservations = jdbcTemplate.query(sql, ((rs, rowNum) -> new Reservation(
                rs.getLong("id"),
                rs.getDate("date").toLocalDate(),
                rs.getTime("time").toLocalTime(),
                rs.getString("name"),
                rs.getLong("theme_id"))),
                id);

        if (reservations.size() == 0) {
            throw new NoSuchReservationException();
        }
        return reservations.get(0);
    }

    public void deleteReservationById(Long id) {
        String deleteSql = "DELETE FROM reservation WHERE id = (?)";
        this.jdbcTemplate.update(deleteSql, id);
    }

    public Optional<Reservation> findReservationByThemId(Long themeId) {
        String sql = "SELECT * FROM reservation WHERE theme_id = (?) LIMIT 1 ";

        List<Reservation> reservations = jdbcTemplate.query(sql, ((rs, rowNum) -> new Reservation(
                        rs.getLong("id"),
                        rs.getDate("date").toLocalDate(),
                        rs.getTime("time").toLocalTime(),
                        rs.getString("name"),
                        rs.getLong("theme_id"))),
                themeId);

        if (reservations.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(reservations.get(0));
    }
}
