package roomescape.repository;

import roomescape.domain.Theme;
import roomescape.dto.ThemeUpdateRequest;

import java.util.Optional;

public interface ThemeRepository {

    Long createTheme(Theme theme);
    Optional<Theme> findThemeById(Long id);
    int updateTheme(ThemeUpdateRequest themeUpdateRequest, Long id);
    int deleteTheme(Long id);


}
