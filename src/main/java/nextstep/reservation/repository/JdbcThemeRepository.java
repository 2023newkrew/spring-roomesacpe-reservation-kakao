package nextstep.reservation.repository;

import nextstep.reservation.entity.Theme;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class JdbcThemeRepository implements ThemeRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcThemeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Theme create(Theme theme) {
        String sql = "insert into THEME (name, desc, price) values (?, ?, ?)";
        KeyHolder themeKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((con -> {
            PreparedStatement psmt = con.prepareStatement(sql, new String[]{"id"});
            psmt.setString(1, theme.getName());
            psmt.setString(2, theme.getDesc());
            psmt.setInt(3, theme.getPrice());

            return psmt;
        }), themeKeyHolder);

        return new Theme(themeKeyHolder.getKey().longValue(),
                theme.getName(),
                theme.getDesc(),
                theme.getPrice());
    }

    @Override
    public List<Theme> findAll() {
        String sql = "select * from THEME";
        List<Theme> themeList = jdbcTemplate.query(sql, themeRowMapper());
        return themeList;
    }

    @Override
    public int deleteById(Long id) {
        String sql = "delete from THEME where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public void clear() {
        String sql = "delete from THEME";
        jdbcTemplate.update(sql);
    }

    private RowMapper<Theme> themeRowMapper() {
        return (rs, rowNum) -> new Theme(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("desc"),
                rs.getInt("price"));
    }
}
