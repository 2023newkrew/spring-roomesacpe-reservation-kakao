package roomescape.service.Theme;
import roomescape.domain.Theme;


public interface ThemeService {
    Theme createTheme(Theme theme);
    Theme lookUpTheme(Long themeId);
    void deleteTheme(Long deleteId);
}
