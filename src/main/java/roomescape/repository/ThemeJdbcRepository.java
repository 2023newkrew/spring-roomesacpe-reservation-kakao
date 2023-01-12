package roomescape.repository;

import roomescape.model.Theme;

import java.util.Optional;

public class ThemeJdbcRepository implements ThemeRepository {
    @Override
    public long save(Theme theme) {
        return 0;
    }

    @Override
    public Optional<Theme> findOneById(Long themeId) {
        return Optional.empty();
    }

    @Override
    public Optional<Theme> findOneByName(String themeName) {
        return Optional.empty();
    }

    @Override
    public void delete(Long themeId) {

    }
}
