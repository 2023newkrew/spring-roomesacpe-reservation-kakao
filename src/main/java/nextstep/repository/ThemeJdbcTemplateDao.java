package nextstep.repository;

import nextstep.domain.Theme;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ThemeJdbcTemplateDao implements ThemeDao{
    private JdbcTemplate jdbcTemplate;

    public ThemeJdbcTemplateDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(Theme theme) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(getPreparedStatementCreatorForSave(theme), keyHolder);
        return keyHolder.getKey().longValue();
    }

    public Optional<Theme> findByName(String name) {
        String sql = "SELECT * FROM theme WHERE name = ?";
        return jdbcTemplate.query(sql, getRowMapper(), name).stream().findAny();
    }

    public Optional<Theme> findById(Long id) {
        String sql = "SELECT * FROM theme WHERE id = ?";
        return jdbcTemplate.query(sql, getRowMapper(), id).stream().findAny();
    }

    public List<Theme> findAll() {
        String sql = "SELECT * FROM theme";
        return jdbcTemplate.query(sql, getRowMapper());
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
