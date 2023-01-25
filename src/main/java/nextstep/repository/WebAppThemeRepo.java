package nextstep.repository;

import nextstep.domain.theme.Theme;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class WebAppThemeRepo implements ThemeRepo {
    private JdbcTemplate jdbcTemplate;

    public WebAppThemeRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long save(Theme theme) {
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

    public int delete(long id) {
        String sql = "DELETE FROM theme WHERE id = ?;";
        return jdbcTemplate.update(sql, id);
    }

    public Theme findById(long id) {
        String sql = "SELECT * FROM theme WHERE id = ?;";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Theme(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("desc"),
                    rs.getInt("price")
            ), id);
        } catch (IncorrectResultSizeDataAccessException ex) {
            return null;
        }
    }
}
