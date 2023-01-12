package nextstep.reservation.repository;

import nextstep.reservation.entity.Theme;

import java.util.List;

public interface ThemeRepository {
    Theme create(Theme theme);

    List<Theme> findAll();

    int deleteById(Long id);

    void clear();
}
