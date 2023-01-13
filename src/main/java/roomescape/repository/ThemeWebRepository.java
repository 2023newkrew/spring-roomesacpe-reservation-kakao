package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Theme;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class ThemeWebRepository implements ThemeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ThemeWebRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Theme> themeRowMapper = (resultSet, rowNum) -> new Theme(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("desc"),
            resultSet.getInt("price")
    );

    @Override
    public List<Theme> findAllThemes() {
        String sql = "SELECT * FROM theme";
        return jdbcTemplate.query(sql, themeRowMapper);
    }

    @Override
    public Optional<Theme> findThemeById(Long id) {
        String sql = "SELECT * FROM theme WHERE id = (?)";
        return jdbcTemplate.query(sql, themeRowMapper, id)
                .stream()
                .findAny();
    }

    @Override
    public Optional<Theme> findThemeByName(String name) {
        String sql = "SELECT * FROM theme WHERE name = (?)";
        return jdbcTemplate.query(sql, themeRowMapper, name)
                .stream()
                .findAny();
    }

    @Override
    public Long insertTheme(Theme theme) {
        String sql = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void changeTheme(Long id, String name, String desc, int price) {
        String sql = "UPDATE theme SET name = (?), desc = (?), price = (?) where id = (?)";
        jdbcTemplate.update(sql, name, desc, price, id);
    }

    @Override
    public void deleteTheme(Long id) {
        String sql = "DELETE FROM theme WHERE id = (?)";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM theme";
        jdbcTemplate.execute(sql);
    }
}
