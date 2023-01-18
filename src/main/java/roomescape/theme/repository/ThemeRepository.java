package roomescape.theme.repository;

import roomescape.theme.domain.Theme;

import java.util.List;

public interface ThemeRepository {
    Theme add(Theme them);

    List<Theme> get();

    Theme get(Long id);

    Theme get(String name);

    void remove(Long id);
}
