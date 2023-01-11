package nextstep.dao;

import lombok.AllArgsConstructor;
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

    private static final RowMapper<ReservationDTO> RESERVATION_DTO_ROW_MAPPER =
            (resultSet, rowNum) -> {
                ThemeDTO theme = new ThemeDTO(
                        resultSet.getString("theme_name"),
                        resultSet.getString("theme_desc"),
                        resultSet.getInt("theme_price")
                );
                return new ReservationDTO(
                        resultSet.getLong("id"),
                        resultSet.getDate("date")
                                .toLocalDate(),
                        resultSet.getTime("time")
                                .toLocalTime(),
                        resultSet.getString("name"),
                        theme
                );
            };

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Boolean existsByDateAndTime(Date date, Time time) throws RuntimeException {
        return jdbcTemplate.queryForObject(SELECT_BY_DATE_AND_TIME_SQL, (r, ignore) -> r.getInt(1) > 0, date, time);
    }

    @Override
    public Long insert(ReservationDTO dto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(getPreparedStatementCreator(dto), keyHolder);
        Number key = keyHolder.getKey();

        return Objects.requireNonNull(key)
                .longValue();
    }

    private PreparedStatementCreator getPreparedStatementCreator(ReservationDTO dto) {
        return connection -> getPrepareStatement(connection, dto);
    }

    private PreparedStatement getPrepareStatement(Connection connection, ReservationDTO dto) throws SQLException {
        var ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
        Date date = Date.valueOf(dto.getDate());
        Time time = Time.valueOf(dto.getTime());
        ThemeDTO theme = dto.getTheme();
        ps.setDate(1, date);
        ps.setTime(2, time);
        ps.setString(3, dto.getName());
        ps.setString(4, theme.getName());
        ps.setString(5, theme.getDesc());
        ps.setInt(6, theme.getPrice());
        return ps;
    }

    @Override
    public ReservationDTO getById(Long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, RESERVATION_DTO_ROW_MAPPER, id);
        }
        catch (Exception ignore) {
            return null;
        }
    }

    @Override
    public Boolean deleteById(Long id) {
        return jdbcTemplate.queryForObject(DELETE_BY_ID_SQL, (r, rowNum) -> rowNum == 1, id);
    }
}
