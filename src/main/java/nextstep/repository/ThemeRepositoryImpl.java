package nextstep.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import nextstep.dto.ThemeCreateDto;
import nextstep.dto.ThemeEditDto;
import nextstep.entity.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;


public class ThemeRepositoryImpl implements ThemeRepository {


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ThemeRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Theme save(ThemeCreateDto themeCreateDto) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("desc", themeCreateDto.getDescription());
        parameters.put("name", themeCreateDto.getName());
        parameters.put("price", themeCreateDto.getPrice());
        long id = new SimpleJdbcInsert(jdbcTemplate).withTableName("THEME").usingGeneratedKeyColumns("id")
                .executeAndReturnKey(parameters).longValue();
        return new Theme(id, themeCreateDto.getName(), themeCreateDto.getDescription(), themeCreateDto.getPrice());
    }

    @Override
    public Optional<Theme> findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM THEME WHERE id = ?",
                (rs, rowNum) ->
                        new Theme(rs.getLong("id"), rs.getString("name"), rs.getString("desc"), rs.getInt("price")),
                id).stream().findAny();

    }

    @Override
    public int update(ThemeEditDto themeEditDto) {
        return jdbcTemplate.update("UPDATE THEME SET name = ?, desc = ?, price = ? WHERE id = ?", themeEditDto.getName(),
                themeEditDto.getDescription(), themeEditDto.getPrice(), themeEditDto.getId());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM THEME WHERE ID = ?", id);
    }
}
