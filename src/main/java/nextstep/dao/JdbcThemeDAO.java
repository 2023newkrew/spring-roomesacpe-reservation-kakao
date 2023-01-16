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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Component
public class JdbcThemeDAO implements ThemeDAO {
    private static final RowMapper<ThemeDTO> THEME_DTO_ROW_MAPPER =
            (resultSet, rowNum) -> {
                return new ThemeDTO(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("desc"),
                        resultSet.getInt("price")
                );
            };
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Long insert(ThemeDTO dto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(getPreparedStatementCreator(dto), keyHolder);
        Number key = keyHolder.getKey();

        return Objects.requireNonNull(key)
                .longValue();
    }

    private PreparedStatementCreator getPreparedStatementCreator(ThemeDTO dto) {
        return connection -> getPrepareStatement(connection, dto);
    }

    private PreparedStatement getPrepareStatement(Connection connection, ThemeDTO dto) throws SQLException {
        var ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});

        ps.setString(1, dto.getName());
        ps.setString(2, dto.getDesc());
        ps.setInt(3, dto.getPrice());

        return ps;
    }

    @Override
    public List<ThemeDTO> getList() {
        try {
            return jdbcTemplate.query(SELECT_SQL, THEME_DTO_ROW_MAPPER);
        }
        catch (Exception ignore) {
            return null;
        }
    }

    @Override
    public Boolean deleteById(Long id) {
        return jdbcTemplate.update(DELETE_BY_ID_SQL, id) == 1;
    }
}
