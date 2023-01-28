package kakao.repository;

import kakao.exception.ReservationNotFoundException;
import kakao.model.Reservation;
import kakao.controller.request.ReservationRequest;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTemplateReservationRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long create(ReservationRequest reservationRequest) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue(Reservation.Column.DATE, Date.valueOf(reservationRequest.getDate()))
                .addValue(Reservation.Column.TIME, Time.valueOf(reservationRequest.getTime()))
                .addValue(Reservation.Column.NAME, reservationRequest.getName())
                .addValue(Reservation.Column.THEME_ID, reservationRequest.getThemeId());

        return jdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM reservation WHERE id=?", reservationResponseRowMapper(), id).stream().findAny();
    }

    @Override
    public Optional<Reservation> findByDateAndTimeAndThemeId(LocalDate date, LocalTime time, Long themeId) {
        return jdbcTemplate
                .query("SELECT * FROM reservation WHERE date=? AND time=? AND theme_id=?", reservationResponseRowMapper(), date, time, themeId)
                .stream().findAny();
    }

    @Override
    public List<Reservation> findByThemeId(Long themeId) {
        return jdbcTemplate.query("SELECT * FROM reservation WHERE theme_id=?", reservationResponseRowMapper(), themeId);
    }

    @Override
    public void deleteById(Long id) {
        if (findById(id).isPresent()) {
            jdbcTemplate.update("DELETE FROM reservation WHERE id=?", id);
            return;
        }
        throw new ReservationNotFoundException();
    }

    private RowMapper<Reservation> reservationResponseRowMapper() {
        return (resultSet, rowNumber) -> {
            Long id = resultSet.getLong(Reservation.Column.ID);
            LocalDate date = resultSet.getDate(Reservation.Column.DATE).toLocalDate();
            LocalTime time = resultSet.getTime(Reservation.Column.TIME).toLocalTime();
            String name = resultSet.getString(Reservation.Column.NAME);
            Long themeId = resultSet.getLong(Reservation.Column.THEME_ID);

            return new Reservation(id, date, time, name, themeId);
        };
    }
}