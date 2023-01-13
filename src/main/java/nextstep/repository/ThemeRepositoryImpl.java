package nextstep.repository;

import static nextstep.repository.ThemeJdbcSql.DELETE_BY_ID;
import static nextstep.repository.ThemeJdbcSql.FIND_BY_ID;
import static nextstep.repository.ThemeJdbcSql.UPDATE;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import nextstep.dto.ThemeCreateDto;
import nextstep.dto.ThemeEditDto;
import nextstep.entity.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;


public class ThemeRepositoryImpl implements ThemeRepository {


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ThemeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(ThemeCreateDto themeCreateDto) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("desc", themeCreateDto.getDescription());
        parameters.put("name", themeCreateDto.getName());
        parameters.put("price", themeCreateDto.getPrice());
        return new SimpleJdbcInsert(jdbcTemplate).withTableName("THEME").usingGeneratedKeyColumns("id")
                .executeAndReturnKey(parameters).longValue();
    }

    @Override
    public Optional<Theme> findById(Long id) {
        return jdbcTemplate.query(FIND_BY_ID,
                (rs, rowNum) ->
                        new Theme(rs.getLong("id"), rs.getString("name"), rs.getString("desc"), rs.getInt("price")),
                id).stream().findAny();

    }

    @Override
    public int update(ThemeEditDto themeEditDto) {
        return jdbcTemplate.update(UPDATE,
                themeEditDto.getName(),
                themeEditDto.getDescription(), themeEditDto.getPrice(), themeEditDto.getId());
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }
}
