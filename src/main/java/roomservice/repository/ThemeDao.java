package roomservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomservice.domain.entity.Theme;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ThemeDao {
    JdbcTemplate jdbcTemplate;
    @Autowired
    public ThemeDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    private RowMapper<Theme> themeRowMapper = (rs, rowNum) ->
        new Theme(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("desc"),
                rs.getInt("price")
        );

    public Long createTheme(Theme theme){
        String sql = "INSERT INTO theme(name, desc, price) VALUES(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement pstmt = connection.prepareStatement(sql, new String[]{"id"});
            pstmt.setString(1, theme.getName());
            pstmt.setString(2, theme.getDesc());
            pstmt.setInt(3, theme.getPrice());
            return pstmt;
        }, keyHolder);
        theme.setId(keyHolder.getKey().longValue());
        return theme.getId();
    }

    public Theme selectThemeById(long id) {
        String sql = "SELECT * FROM theme WHERE id = ?";
        List<Theme> result = jdbcTemplate.query(sql, themeRowMapper, id);
        return result.size()==0 ? null : result.get(0);
    }

    public void deleteThemeById(Long id) {
        String sql = "DELETE FROM theme WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
