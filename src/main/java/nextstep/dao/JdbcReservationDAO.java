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

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

@AllArgsConstructor
@Component
public class JdbcReservationDAO implements ReservationDAO {

    private static final String INSERT_IF_NOT_EXISTS_DATE_TIME_SQL =
            "INSERT INTO reservation(date,time,name,theme_name,theme_desc,theme_price)" +
                    "VALUES(?,?,?,?,?,?)" +
                    "IF NOT EXISTS (" +
                    "   SELECT * " +
                    "   FROM reservation" +
                    "   WHERE date = ? AND time = ?" +
                    ")";

    private static final String SELECT_BY_ID_SQL =
            "SELECT * " +
                    "FROM reserevation " +
                    "WHERE id = ?";

    private static final String DELETE_BY_ID_SQL = "DELETE " +
            "FROM reservation " +
            "WHERE id = ?";

    private static final RowMapper<ReservationDTO> RESERVATION_DTO_ROW_MAPPER =
            (resultSet, rowNum) -> {
                ThemeDTO theme = new ThemeDTO(
                        resultSet.getString("theme_name"),
                        resultSet.getString("them_desc"),
                        resultSet.getInt("theme_price")
                );
                return new ReservationDTO(
                        resultSet.getLong("id"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getTime("time").toLocalTime(),
                        resultSet.getString("name"),
                        theme
                );
            };

    private final JdbcTemplate jdbcTemplate;


    @Override
    public Long insertIfNotExistsDateTime(ReservationDTO dto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(getPreparedStatementCreator(dto), keyHolder);
        Number key = keyHolder.getKey();

        return key.longValue();
    }

    private PreparedStatementCreator getPreparedStatementCreator(ReservationDTO dto) {
        return connection -> getPrepareStatement(connection, dto);
    }

    private PreparedStatement getPrepareStatement(Connection connection, ReservationDTO dto) throws SQLException {
        var ps = connection.prepareStatement(INSERT_IF_NOT_EXISTS_DATE_TIME_SQL, new String[]{"id"});
        Date date = Date.valueOf(dto.getDate());
        Time time = Time.valueOf(dto.getTime());
        ThemeDTO theme = dto.getTheme();
        ps.setDate(1, date);
        ps.setTime(2, time);
        ps.setString(3, dto.getName());
        ps.setString(4, theme.getName());
        ps.setString(5, theme.getDesc());
        ps.setInt(6, theme.getPrice());
        ps.setDate(7, date);
        ps.setTime(8, time);
        return ps;
    }

    @Override
    public ReservationDTO getById(Long id) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, RESERVATION_DTO_ROW_MAPPER, id);
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_BY_ID_SQL, id);
    }
}
