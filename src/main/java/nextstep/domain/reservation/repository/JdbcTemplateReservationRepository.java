package nextstep.domain.reservation.repository;

import nextstep.domain.reservation.Reservation;
import nextstep.domain.theme.Theme;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static nextstep.domain.QuerySetting.Reservation.*;

@Repository
public class JdbcTemplateReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) -> new Reservation(
            rs.getLong("r_id"),
            LocalDate.parse(rs.getString("date")),
            LocalTime.parse(rs.getString("time")),
            rs.getString("r_name"),
            new Theme(
                    rs.getString("t_name"),
                    rs.getString("desc"),
                    rs.getInt("price")
            )
    );

    public JdbcTemplateReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(PK_NAME);
    }

    @Override
    public Reservation save(Reservation reservation) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("date", reservation.getDate())
                .addValue("time", reservation.getTime())
                .addValue("name", reservation.getName())
                .addValue("theme_id", reservation.getTheme().getId());
        Long reservationId = jdbcInsert.executeAndReturnKey(parameterSource).longValue();

        return new Reservation(reservationId, reservation);
    }

    @Override
    public Optional<Reservation> findById(Long reservationId) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SELECT_BY_ID, reservationRowMapper, reservationId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Boolean existsByThemeId(Long themeId) {
        return jdbcTemplate.query(SELECT_ONE_BY_THEME_ID, ResultSet::next, themeId);
    }

    @Override
    public Boolean existsByThemeIdAndDateAndTime(Long themeId, LocalDate date, LocalTime time) {
        return jdbcTemplate.query(
                SELECT_ONE_BY_THEME_ID_AND_DATE_AND_TIME,
                ResultSet::next,
                themeId, Date.valueOf(date), Time.valueOf(time));
    }

    @Override
    public Boolean deleteById(Long reservationId) {
        return jdbcTemplate.update(DELETE_BY_ID, reservationId) > 0;
    }

}
