package nextstep.theme.repository;

import nextstep.theme.domain.Theme;

import java.util.List;

public interface ThemeRepository {

    Theme insert(Theme theme);

    Theme getById(Long id);

    List<Theme> getAll();

    Theme update(Theme theme);

    boolean delete(Long id);
}
