package nextstep.reservation.repository;

import nextstep.reservation.entity.Theme;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository {
    Theme save(Theme theme);

    Optional<Theme> findById(long id);

    List<Theme> findAll();

    int deleteById(long id);

    void clear();
}
