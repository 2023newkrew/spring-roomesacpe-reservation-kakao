package roomescape.repository;

import roomescape.model.Theme;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository {
    long save(Theme theme);
    Optional<Theme> findOneById(Long themeId);
    Optional<Theme> findOneByName(String themeName);
    List<Theme> findAll();
    void delete(Long themeId);
    Boolean hasThemeWithName(String themeName);
    void updateNameOfId(Long themeId, String newName);
    void updateDescOfId(Long themeId, String newDesc);
    void updatePriceOfId(Long themeId, Integer newPrice);
}
