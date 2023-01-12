package roomescape.repository;

import roomescape.domain.Theme;

public interface ThemeRepository {
    String FIND_THEME_BY_ID_SQL = "SELECT * FROM theme WHERE id = ?;";
    String UPDATE_THEME_SQL = "UPDATE theme SET name = ?, desc = ?, price = ? WHERE id = ?;";
    String DELETE_THEME_SQL = "DELETE FROM theme WHERE id = ?;";

    Long createTheme(Theme theme);
    Theme findThemeById(Long id);
    int updateTheme(Theme theme);
    int deleteTheme(Long id);


}
