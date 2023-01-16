package nextstep.main.java.nextstep.repository;

import nextstep.main.java.nextstep.domain.Theme;
import nextstep.main.java.nextstep.repositoryUtil.CustomPreparedStatementCreator;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcThemeRepository implements ThemeRepository {
    JdbcTemplate jdbcTemplate;

    public JdbcThemeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Theme save(Theme theme) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((con -> CustomPreparedStatementCreator.insertThemePreparedStatement(con, theme)), keyHolder);
        return new Theme(keyHolder.getKey()
                .longValue(), theme);
    }

    @Override
    public Optional<Theme> findById(Long id) {
        String sql = "SELECT * FROM theme WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, (rs, rowNum) -> Theme.of(rs), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Theme> findByName(String name) {
        String sql = "SELECT * FROM theme WHERE name = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, (rs, rowNum) -> Theme.of(rs), name));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Theme> findAll() {
        String sql = "SELECT * FROM theme";
        return jdbcTemplate.query(sql, ((rs, rowNum) -> Theme.of(rs)));
    }

    @Override
    public int update(Theme theme) {
        final String sql = "UPDATE theme SET (name, desc, price) = (?, ?, ?) WHERE id = ?;";
        return jdbcTemplate.update(sql, theme.getName(), theme.getDesc(), theme.getPrice(), theme.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM theme WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
