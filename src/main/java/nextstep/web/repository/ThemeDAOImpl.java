package nextstep.web.repository;

import java.sql.PreparedStatement;
import java.util.List;
import nextstep.domain.Theme;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ThemeDAOImpl implements ThemeDAO{
    private final RowMapper<Theme> themeRowMapper = (resultSet, rowNum) -> {
        Theme theme = new Theme(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("desc"),
                resultSet.getInt("price")
        );
        return theme;
    };
    private JdbcTemplate jdbcTemplate;

    public ThemeDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long insertWithKeyHolder(Theme theme) {
        String sql = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public Theme findById(Long id) {
        String sql = "select * from theme where id = ?";
        return jdbcTemplate.queryForObject(sql, themeRowMapper, id);
    }

    @Override
    public List<Theme> findByName(String name) {
        String sql = "select * from theme where name = ?";
        return jdbcTemplate.query(sql, themeRowMapper, name);
    }

    @Override
    public List<Theme> getAllThemes() {
        String sql = "select * from theme";
        return jdbcTemplate.query(sql, themeRowMapper);
    }

    @Override
    public Integer delete(Long id) {
        String sql = "delete from theme where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
