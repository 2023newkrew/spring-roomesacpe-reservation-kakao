package roomescape.repository.Theme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Theme;
import roomescape.repository.DBMapper.DatabaseMapper;
import roomescape.repository.DBMapper.H2Mapper;

import java.sql.PreparedStatement;
import java.util.Optional;

@Repository
public class JdbcThemeRepository implements ThemeRepository{
    public final JdbcTemplate jdbcTemplate;
    private final DatabaseMapper databaseMapper;

    @Autowired
    public JdbcThemeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.databaseMapper = new H2Mapper();
    }

    @Override
    public Long createTheme(Theme theme) {
        String sql = "INSERT INTO THEME (name, desc, price) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = autoIncrementKeyStatement(sql, theme);
        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return databaseMapper.getAutoKey(keyHolder);
    }

    @Override
    public Optional<Theme> findThemeById(long themeId) {
        String sql = "select name, desc, price from THEME where id = ?";
        Theme theme = jdbcTemplate.queryForObject(sql, databaseMapper.themeRowMapper(themeId), themeId);
        return Optional.ofNullable(theme);
    }

    @Override
    public Long findCountByNameAndPrice(Theme theme) {
        String sql = "select count(*) from THEME where name = ? AND price = ?";
        return jdbcTemplate.queryForObject(sql, Long.class,
                theme.getName(), theme.getPrice());
    }

    @Override
    public Integer deleteTheme(long deleteId) {
        String sql = "DELETE FROM THEME WHERE id=?";
        return jdbcTemplate.update(sql, deleteId);
    }

    @Override
    public Boolean isThemeExists(long themeId) {
        String sql = "SELECT EXISTS(SELECT 1 FROM THEME where id = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, themeId);
    }

    private PreparedStatementCreator autoIncrementKeyStatement(String sql, Theme theme) {
        return (connection) -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            return ps;
        };
    }


}
