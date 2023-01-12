package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.model.Theme;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ThemeMemoryRepository implements ThemeRepository {
    private final Map<Long, Theme> themes;
    private final AtomicLong themeIdIndex;

    ThemeMemoryRepository() {
        themes = new ConcurrentHashMap<>();
        themeIdIndex = new AtomicLong();
    }

    @Override
    public Long save(Theme theme) {
        Long id = themeIdIndex.getAndIncrement();
        theme.setId(id);
        themes.put(id, theme);
        return id;
    }

    @Override
    public Optional<Theme> find(Long id) {
        return Optional.ofNullable(themes.get(id));
    }

    @Override
    public Integer delete(Long id) {
        themes.remove(id);
        return 1;
    }

    @Override
    public Boolean has(String name) {
        return themes.values()
                .stream()
                .anyMatch(theme -> theme.getName().equals(name));
    }
}
