package nextstep.roomescape.theme.service;


import nextstep.roomescape.theme.repository.model.Theme;

import java.util.List;

public interface ThemeService {
    Long create(Theme theme);

    List<Theme> findAll();

    void delete(long id);

}
