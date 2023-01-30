package roomescape.reservation.repository.common;

import roomescape.reservation.domain.Theme;

import java.util.List;

public interface ThemeRepository {
    Theme add(Theme theme);

    List<Theme> get();

    Theme get(Long id);

    Theme get(String name);

    void remove(Long id);
}
