package kakao.repository;

import domain.Theme;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Repository
public class ThemeMemRepository implements ThemeRepository {
    private final Map<Long, Theme> themes = new TreeMap<>();

    long id = 0;

    @Override
    public long save(Theme theme) {
        themes.put(++id, theme);
        return id;
    }

    @Override
    public Theme findById(long id) {
        return themes.get(id);
    }

    @Override
    public List<Theme> themes() {
        return new ArrayList<>(themes.values());
    }

    @Override
    public List<Theme> findByName(String name) {
        return themes.values().stream().filter(e -> e.getName().equals(name)).collect(Collectors.toList());
    }

    @Override
    public int update(String name, String desc, Integer price, long id) {
        if (!themes.containsKey(id)) return 0;
        themes.put(id, new Theme(id, name, desc, price));
        return 1;
    }

    @Override
    public int delete(long id) {
        if (!themes.containsKey(id)) return 0;
        themes.remove(id);
        return 1;
    }
}
