package nextstep.dao.DAOImple;

import lombok.AllArgsConstructor;
import nextstep.dao.ReservationDAO;
import nextstep.domain.Reservation;
import nextstep.dto.ReservationDTO;
import nextstep.dto.ThemeDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.Objects;

@AllArgsConstructor
@Component
public class JdbcReservationDAO implements ReservationDAO {
    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER =
            (resultSet, rowNum) -> {
                return new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getDate("date")
                                .toLocalDate(),
                        resultSet.getTime("time")
                                .toLocalTime(),
                        resultSet.getString("name"),
                        resultSet.getLong("theme_id")
                );
            };

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Boolean existsByDateTime(Date date, Time time) throws RuntimeException {
        return jdbcTemplate.queryForObject(SELECT_BY_DATE_AND_TIME_SQL, (r, ignore) -> r.getInt(1) > 0, date, time);
    }

    @Override
    public Long insert(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setLong(4, reservation.getTheme_id());
            return ps;

        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public Reservation getById(Long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, RESERVATION_ROW_MAPPER, id);
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean deleteById(Long id) {
        return jdbcTemplate.update(DELETE_BY_ID_SQL, id) == 1;
    }
}
