package nextstep.repository.theme;

import nextstep.domain.Theme;

import java.util.List;

public interface ThemeRepository {
    Theme add(Theme theme);

    List<Theme> findAll();
}
