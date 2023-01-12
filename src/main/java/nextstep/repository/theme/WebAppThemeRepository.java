package nextstep.repository.theme;

import nextstep.domain.theme.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;

@Repository
public class WebAppThemeRepository implements ThemeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WebAppThemeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long add(Theme theme) {
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

    @Override
    public List<Theme> findAll() {
        String sql = "SELECT * FROM theme;";
        return jdbcTemplate.query(sql, (rs, rowNum)
                        -> new Theme(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("desc"),
                        rs.getInt("price")
                )
        );
    }

    @Override
    public int updateTheme(Theme theme) {
        String sql = "UPDATE THEME SET name = ?, desc = ?, price = ? WHERE id = ?;";
        return jdbcTemplate.update(sql, theme.getName(), theme.getDesc(), theme.getPrice(), theme.getId());
    }

    @Override
    public int deleteTheme(long id) {
        return 0;
    }
}
