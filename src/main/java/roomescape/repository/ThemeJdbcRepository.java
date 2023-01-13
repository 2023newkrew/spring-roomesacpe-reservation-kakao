package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.Theme;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ThemeJdbcRepository implements ThemeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertActor;

    public ThemeJdbcRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("theme")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Theme> actorRowMapper = (resultSet, rowNum) -> (
            new Theme(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("desc"),
                    resultSet.getInt("price")
            )
    );

    @Override
    public Long save(Theme theme) {
        Map<String, String> parameters = Map.of(
                "name", theme.getName(),
                "desc", theme.getDesc(),
                "price", theme.getPrice().toString()
        );
        return insertActor.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public Optional<Theme> find(Long id) {
        String sql = "select * from theme where id = ? limit 1";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, actorRowMapper, id));
    }

    @Override
    public List<Theme> findAll() {
        String sql = "select * from theme";
        return jdbcTemplate.query(sql, actorRowMapper);
    }

    @Override
    public Boolean delete(Long id) {
        String sql = "delete from theme where id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public Boolean existsByName(String name) {
        String sql = "select * from theme where name = ? limit 1";
        return jdbcTemplate.query(sql, actorRowMapper, name).size() > 0;
    }
}
