package nextstep.domain.theme.repository;

import nextstep.domain.theme.domain.Theme;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class ThemeJdbcTemplateRepository implements ThemeRepository {

    private JdbcTemplate jdbcTemplate;

    public ThemeJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Theme> themeRowMapper = (rs, rowNum) ->
            new Theme(rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("desc"),
                    rs.getInt("price"));

    public Optional<Theme> findByName(String name) {
        try {
            String sql = "SELECT * FROM theme WHERE name = ?";
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, themeRowMapper, name));
        } catch (
                EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Long save(Theme theme) {
        String sql = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?);";
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

    public Optional<Theme> findById(Long id) {
        try {
            String sql = "SELECT * FROM theme WHERE id = ?";
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, themeRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Theme> findAll() {
        String sql = "SELECT * FROM theme";
        return jdbcTemplate.query(sql, themeRowMapper);
    }

    @Override
    public void update(Theme theme) {
        String sql = "UPDATE theme SET name = ?, desc = ?, price = ? WHERE id = ?";
        jdbcTemplate.update(sql, theme.getId(), theme.getName(), theme.getDesc(), theme.getPrice());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM theme WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void clear() {
        String sql = "DELETE FROM theme";
        jdbcTemplate.update(sql);
        resetId();
    }

    private void resetId() {
        String sql = "ALTER TABLE theme ALTER COLUMN id RESTART WITH 1";
        jdbcTemplate.execute(sql);
    }
}
