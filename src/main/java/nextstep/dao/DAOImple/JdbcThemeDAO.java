package nextstep.dao.DAOImple;

import lombok.AllArgsConstructor;
import nextstep.dao.ThemeDAO;
import nextstep.domain.Theme;
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
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Component
public class JdbcThemeDAO implements ThemeDAO {
    private static final RowMapper<Theme> THEME_ROW_MAPPER =
            (resultSet, rowNum) -> {
                return new Theme(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("desc"),
                        resultSet.getInt("price")
                );
            };
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Long insert(Theme theme) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            return ps;

        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public List<Theme> getList() {
        try {
            return jdbcTemplate.query(SELECT_SQL, THEME_ROW_MAPPER);
        }
        catch (Exception ignore) {
            return null;
        }
    }

    @Override
    public Theme getById(Long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, THEME_ROW_MAPPER, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean deleteById(Long id) {
        return jdbcTemplate.update(DELETE_BY_ID_SQL, id) == 1;
    }
}
