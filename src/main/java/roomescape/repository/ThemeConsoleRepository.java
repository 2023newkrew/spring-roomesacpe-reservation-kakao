package roomescape.repository;

import org.springframework.jdbc.core.RowMapper;
import roomescape.domain.Theme;
import roomescape.dto.ThemeUpdateRequest;

import java.util.Optional;

public class ThemeConsoleRepository extends BaseConsoleRepository implements ThemeRepository {

    private static final RowMapper<Optional<Theme>> ROW_MAPPER = (rs, rowNum) -> {
        Optional<Theme> theme = Optional.of(new Theme(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("desc"),
                rs.getInt("price")
        ));
        return theme;
    };

    @Override
    public Long createTheme(Theme theme) {
        final String SQL = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?);";
        return insert(SQL, theme.getName(), theme.getDesc(), theme.getPrice());
    }

    @Override
    public Optional<Theme> findThemeById(Long id) {
        final String SQL = "SELECT * FROM theme WHERE id = ?;";
        return query(SQL, ROW_MAPPER, id).get(0);
    }

    @Override
    public int updateTheme(ThemeUpdateRequest themeUpdateRequest, Long id) {
        final String SQL = "UPDATE theme SET name = ?, desc = ?, price = ? WHERE id = ?;";
        return update(SQL, themeUpdateRequest.getName(), themeUpdateRequest.getDesc(), themeUpdateRequest.getPrice(), id);
    }

    @Override
    public int deleteTheme(Long id) {
        final String SQL = "DELETE FROM theme WHERE id = ?;";
        return delete(SQL, id);
    }

}
