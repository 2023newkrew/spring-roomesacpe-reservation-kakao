package nextstep.web;

import nextstep.model.Theme;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Optional;

@Repository
public class JdbcTemplateThemeRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateThemeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Theme save(Theme theme) {
        String sql = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});

            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            return ps;
        }, keyHolder);

        return new Theme(keyHolder.getKeyAs(Long.class), theme.getName(), theme.getDesc(), theme.getPrice());
    }

    public Optional<Theme> findById(Long id) {
        try {
            String sql = "SELECT id, name, desc, price FROM theme WHERE id = ?";
            RowMapper<Theme> mapper = (rs, rowNum) ->
                    new Theme(id, rs.getString("name"), rs.getString("desc"), rs.getInt("price"));

            return Optional.of(jdbcTemplate.queryForObject(sql, mapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM theme WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }
}
