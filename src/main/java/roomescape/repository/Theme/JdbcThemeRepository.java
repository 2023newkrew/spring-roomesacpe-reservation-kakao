package roomescape.repository.Theme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Theme;

import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.Optional;

@Repository
public class JdbcThemeRepository implements ThemeRepository{
    public final JdbcTemplate jdbcTemplate;
    @Autowired
    public JdbcThemeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long createTheme(Theme theme) {
        String sql = "INSERT INTO THEME (name, desc, price) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            return ps;
        };
        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Optional<Theme> findById(long themeId) {
        String sql = "select name, desc, price from THEME where id = ?";
        Theme theme = jdbcTemplate.queryForObject(sql,
                new Object[]{themeId},
                (rs, rowNum) -> {
                    String name = rs.getString("name");
                    String desc = rs.getString("price");
                    int price = rs.getInt("price");
                    return new Theme(name, desc, price);
                });
        return Optional.ofNullable(theme);
    }

    @Override
    public Integer findIdByDateAndTime(Theme theme) {
        String sql = "select count(*) from THEME where name = ? AND price = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class,
                theme.getName(), theme.getPrice());
    }

    @Override
    public Integer deleteTheme(long deleteId) {
        String sql = "DELETE FROM THEME WHERE id=?";
        return jdbcTemplate.update(sql, deleteId);
    }
}
