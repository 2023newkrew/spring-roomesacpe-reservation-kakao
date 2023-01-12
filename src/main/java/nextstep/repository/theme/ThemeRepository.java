package nextstep.repository.theme;

import nextstep.domain.theme.Theme;

import java.util.List;

public interface ThemeRepository {

    long add(Theme theme);

    List<Theme> findAll();

    int updateTheme(Theme theme);

    int deleteTheme(long id);
}
