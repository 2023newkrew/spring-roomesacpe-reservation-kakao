package web.repository;

import web.entity.Theme;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository {

    Long createTheme(Theme theme);

    Integer deleteTheme(long themeId);

    Optional<List<Theme>> getThemes();
}
