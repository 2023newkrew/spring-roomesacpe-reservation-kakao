package roomservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomservice.domain.entity.Theme;
import roomservice.exceptions.exception.DuplicatedThemeNameException;
import roomservice.exceptions.exception.ReferencedThemeDeletionException;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * ThemeDao is responsible for THEME table.
 */
@Repository
public class ThemeDao {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public ThemeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Theme> themeRowMapper = (rs, rowNum) ->
            new Theme(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("desc"),
                    rs.getInt("price")
            );

    /**
     * insert a theme into DB.
     *
     * @param theme to be inserted into DB.
     * @return id automatically given by DB.
     */
    public Long createTheme(Theme theme) {
        checkNameDuplication(theme);
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

    private void checkNameDuplication(Theme theme) {
        String sql = "SELECT COUNT(*) FROM THEME WHERE name = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, theme.getName());
        if (count >= 1) {
            throw new DuplicatedThemeNameException();
        }
    }

    /**
     * query all themes from db.
     *
     * @return List of themes.
     */
    public List<Theme> selectAllTheme() {
        String sql = "SELECT * FROM theme";
        List<Theme> result = jdbcTemplate.query(sql, themeRowMapper);
        return result;
    }

    /**
     * query one theme specified by id.
     *
     * @param id which you want to find.
     * @return theme if found, null if not found.
     */
    public Theme selectThemeById(long id) {
        String sql = "SELECT * FROM theme WHERE id = ?";
        List<Theme> result = jdbcTemplate.query(sql, themeRowMapper, id);
        return result.size() == 0 ? null : result.get(0);
    }

    public Theme selectThemeByName(String name) {
        String sql = "SELECT * FROM theme WHERE name = ?";
        List<Theme> result = jdbcTemplate.query(sql, themeRowMapper, name);
        return result.size() == 0 ? null : result.get(0);
    }

    /**
     * delete one theme specified by id.
     *
     * @param id which you want to delete.
     */
    public void deleteThemeById(Long id) {
        try {
            String sql = "DELETE FROM theme WHERE id = ?";
            jdbcTemplate.update(sql, id);
        } catch(DataIntegrityViolationException e){
            throw new ReferencedThemeDeletionException();
        }
    }
}
