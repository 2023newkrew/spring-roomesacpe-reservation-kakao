package nextstep.domain.repository;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import org.springframework.dao.EmptyResultDataAccessException;
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
import java.util.Optional;

import static nextstep.domain.repository.QuerySetting.Reservation.*;

@Repository
public class JdbcTemplateReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) -> new Reservation(
            rs.getLong("id"),
            LocalDate.parse(rs.getString("date")),
            LocalTime.parse(rs.getString("time")),
            rs.getString("name"),
            new Theme(
                    rs.getString("theme_name"),
                    rs.getString("theme_desc"),
                    rs.getInt("theme_price")
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
                .addValue("theme_name", reservation.getTheme().getName())
                .addValue("theme_desc", reservation.getTheme().getDesc())
                .addValue("theme_price", reservation.getTheme().getPrice());
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
    public boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        return jdbcTemplate.queryForObject(SELECT_COUNT_BY_DATE_AND_TIME, Integer.class, Date.valueOf(date), Time.valueOf(time)) > 0;
    }

    @Override
    public boolean deleteById(Long reservationId) {
        return jdbcTemplate.update(DELETE_BY_ID, reservationId) > 0;
    }

}
