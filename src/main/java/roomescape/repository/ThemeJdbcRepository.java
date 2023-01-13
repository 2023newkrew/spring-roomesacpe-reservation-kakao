package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.dto.ThemeUpdateDto;
import roomescape.model.Theme;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Optional;

@Repository
public class ThemeJdbcRepository implements ThemeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertActor;

    public ThemeJdbcRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("THEME")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Theme> actorRowMapper = (resultSet, rowNum) -> {
        return new Theme(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("desc"),
                resultSet.getInt("price")
        );
    };

    @Override
    public long save(Theme theme) {
        HashMap<String , String > parameters = new HashMap<>();
        parameters.put("name", theme.getName());
        parameters.put("desc", theme.getDesc());
        parameters.put("price", theme.getPrice().toString());
        return insertActor.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public Optional<Theme> findOneById(Long themeId) {
        String sql = "select * from theme where id = ? limit 1";
        return jdbcTemplate.query(sql, actorRowMapper, themeId).stream().findFirst();
    }

    @Override
    public Optional<Theme> findOneByName(String themeName) {
        String sql = "select * from theme where name = ? limit 1";
        return jdbcTemplate.query(sql, actorRowMapper, themeName).stream().findFirst();
    }

    @Override
    public void delete(Long themeId) {
        String sql = "delete from theme where id = ?";
        jdbcTemplate.update(sql, themeId);
    }

    @Override
    public Boolean hasThemeWithName(String themeName) {
        String sql = "select * from theme where name = ?";
        return jdbcTemplate.query(sql, actorRowMapper, themeName).size() > 0;
    }

    @Override
    public void updateNameOfId(Long themeId, String newName) {
        String sql = "update theme set name = ? where id = ?";
        jdbcTemplate.update(sql, newName, themeId);
    }

    @Override
    public void updateDescOfId(Long themeId, String newDesc) {
        String sql = "update theme set desc = ? where id = ?";
        jdbcTemplate.update(sql, newDesc, themeId);
    }

    @Override
    public void updatePriceOfId(Long themeId, Integer newPrice) {
        String sql = "update theme set price = ? where id = ?";
        jdbcTemplate.update(sql, newPrice, themeId);
    }
}
