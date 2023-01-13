package nextstep.repository;

import java.sql.PreparedStatement;
import java.util.List;
import nextstep.model.Theme;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ThemeRepository {
    private static final RowMapper<Theme> THEME_ROW_MAPPER = (rs, rn) -> {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        String desc = rs.getString("desc");
        int price = rs.getInt("price");
        return new Theme(id, name, desc, price);
    };

    private final JdbcTemplate jdbcTemplate;

    public ThemeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Theme save(Theme theme) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?)";

        PreparedStatementCreator creator = con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            return ps;
        };

        jdbcTemplate.update(creator, keyHolder);
        long id = keyHolder.getKey().longValue();

        return new Theme(id, theme.getName(), theme.getDesc(), theme.getPrice());
    }

    public List<Theme> findAll() {
        return jdbcTemplate.query("SELECT id, name, desc, price FROM theme", THEME_ROW_MAPPER);
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM theme WHERE id = ?", id);
    }
}