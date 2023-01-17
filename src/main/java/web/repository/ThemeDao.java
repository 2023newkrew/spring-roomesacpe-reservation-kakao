package web.repository;

import web.entity.Theme;

import java.util.List;
import java.util.Optional;

public interface ThemeDao {
    Long createTheme(Theme theme);

    Long deleteTheme(long themeId);

    Optional<List<Theme>> getThemes();
}
