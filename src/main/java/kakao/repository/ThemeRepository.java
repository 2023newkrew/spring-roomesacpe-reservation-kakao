package kakao.repository;

import domain.Theme;

import java.util.List;

public interface ThemeRepository {
    long save(Theme theme);

    List<Theme> themes();

    Theme findById(long id);

    List<Theme> findByName(String name);

    int update(String name, String desc, Integer price, long id);

    int delete(long id);

}
