package roomescape.repository;

import roomescape.domain.Theme;

public interface ThemeRepository {
    static final String FIND_THEME_BY_ID_SQL = "SELECT * FROM theme WHERE id = ?;";
    static final String UPDATE_THEME_SQL = "UPDATE theme SET name = ?, desc = ?, price = ? WHERE id = ?;";
    static final String DELETE_THEME_SQL = "DELETE FROM theme WHERE id = ?;";

    public Long createTheme(Theme theme);
    public Theme findThemeById(Long id);
    public int updateTheme(Theme theme);
    public int deleteTheme(Long id);


}
