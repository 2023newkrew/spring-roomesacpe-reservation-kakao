package nextstep.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import nextstep.model.Theme;

public class ConsoleThemeRepository implements ThemeRepository {

    private Map<Long, Theme> themes;
    private long id;

    public ConsoleThemeRepository() {
        themes = new HashMap<>();
        id = 1L;
    }

    @Override
    public Theme save(Theme theme) {
        Theme newTheme = new Theme(id++, theme.getName(), theme.getDesc(), theme.getPrice());
        themes.put(newTheme.getId(), newTheme);
        return newTheme;
    }

    @Override
    public List<Theme> findAll() {
        return new ArrayList<>(themes.values());
    }

    @Override
    public void deleteById(Long id) {
        themes.remove(id);
    }

    @Override
    public void deleteAll() {
        themes.clear();
    }

    @Override
    public Optional<Theme> findById(Long themeId) {
        return Optional.ofNullable(themes.get(themeId));
    }
}
