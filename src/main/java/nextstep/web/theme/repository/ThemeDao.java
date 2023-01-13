package nextstep.web.theme.repository;

import lombok.RequiredArgsConstructor;
import nextstep.domain.Theme;
import nextstep.web.common.exception.BusinessException;
import nextstep.web.common.exception.CommonErrorCode;
import nextstep.web.common.repository.RoomEscapeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ThemeDao implements RoomEscapeRepository<Theme> {

    public static final String TABLE_NAME = "theme";
    public static final String KEY_COLUMN_NAME = "id";
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Theme> themeRowMapper = (resultSet, rowNum) -> Theme.from(resultSet);

    public Long save(Theme theme) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(KEY_COLUMN_NAME);

        Number key = simpleJdbcInsert.executeAndReturnKey(prepareParams(theme));

        return key.longValue();
    }


    public Theme findById(Long id) {
        String sql = "SELECT * FROM THEME WHERE ID = ?;";
        List<Theme> themes = jdbcTemplate.query(sql, themeRowMapper, id);
        if (themes.isEmpty()) {
            throw new BusinessException(CommonErrorCode.RESOURCE_NOT_FOUND);
        }

        return themes.get(0);
    }

    public List<Theme> findAll() {
        String sql = "SELECT * FROM THEME;";

        return jdbcTemplate.query(sql, themeRowMapper);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM THEME WHERE ID = ?;";
        if (jdbcTemplate.update(sql, id) == 0) {
            throw new BusinessException(CommonErrorCode.RESOURCE_NOT_FOUND);
        }
    }

    public int updateById(Theme theme) {
        String sql = "UPDATE THEME SET name = ?, desc = ?, price = ? WHERE ID = ?;";

        return jdbcTemplate.update(sql, theme.getName(), theme.getDesc(), theme.getPrice(), theme.getId());
    }

    private Map<String, Object> prepareParams(Theme theme) {
        return Map.of(
                "name", theme.getName(),
                "desc", theme.getDesc(),
                "price", theme.getPrice()
        );
    }
}
