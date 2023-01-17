package nextstep.domain.repository;

import nextstep.domain.Theme;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class SimpleThemeRepository implements ThemeRepository{

    private final List<Theme> themes;
    private final AtomicLong id;

    public SimpleThemeRepository() {
        this.themes = new ArrayList<>();
        this.id = new AtomicLong(1L);
    }

    @Override
    public Long save(Theme theme) {
        Long themeId = id.getAndIncrement();
        themes.add(new Theme(themeId, theme.getName(), theme.getDesc(), theme.getPrice()));
        return themeId;
    }

    @Override
    public Optional<Theme> findThemeById(Long id) {
        return themes.stream().filter(theme -> theme.isSameId(id)).findAny();
    }

    @Override
    public boolean existByThemeName(String name) {
        return themes.stream().filter(theme -> theme.isSameName(name)).count() > 0L;
    }

    @Override
    public List<Theme> getAllThemes() {
        return this.themes;
    }

    @Override
    public boolean deleteThemeById(Long id) {
        return themes.removeIf(theme -> theme.isSameId(id));
    }
}
