package web.theme.repository;


import web.entity.Theme;

import java.util.Optional;

public class ThemeRepository {

    public long save(Theme theme) {
        return 0;
    }

    public Optional<Theme> findById(long themeId) {
        return Optional.empty();
    }

    public Long delete(long themeId) {
        return null;
    }

    public void clearAll() {

    }
}
