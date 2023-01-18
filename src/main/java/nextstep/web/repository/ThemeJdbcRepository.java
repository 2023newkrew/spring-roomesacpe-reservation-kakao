package nextstep.web.repository;

import nextstep.domain.Theme;
import nextstep.domain.repository.ThemeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ThemeJdbcRepository implements ThemeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ThemeJdbcRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Theme create(Theme theme) {
        String sql = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            return ps;
        }, keyHolder);
        theme.setId(keyHolder.getKey().longValue());
        return theme;
    }

    @Override
    public Theme find(long id) {
        String sql = "SELECT * FROM theme WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, themeRowMapper, id);
    }

    @Override
    public List<Theme> findAll() {
        String sql = "SELECT * FROM theme";
        return jdbcTemplate.query(sql, themeRowMapper);
    }

    @Override
    public boolean delete(long id) {
        String sql = "DELETE FROM theme WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    private RowMapper<Theme> themeRowMapper = (rs, rowNum) -> {
        return new Theme(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("desc"),
                rs.getInt("price")
        );
    };
}