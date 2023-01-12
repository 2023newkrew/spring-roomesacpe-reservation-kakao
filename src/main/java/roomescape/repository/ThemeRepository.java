package roomescape.repository;

import roomescape.domain.Theme;

public interface ThemeRepository {

    public Long createTheme(Theme theme);
    public Theme findThemeById(Long id);
    public Theme findThemeByName(String name);
    public int updateTheme(Theme theme);
    public int deleteTheme(Long id);


}
