package roomescape.service.Theme;
import roomescape.domain.Theme;


public interface ThemeService {
    String createTheme(Theme theme);
    String lookUpTheme(Long themeId);
    void deleteTheme(Long deleteId);
}
