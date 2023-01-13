package roomescape.theme.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import roomescape.entity.Theme;

@Service
public interface ThemeRepository {
    Long save(Theme theme);

    Optional<Theme> findById(Long themeId);
    List<Theme> findAll();
    int delete(Long themeId);

    boolean isThemeNameDuplicated(String themeName);
}
