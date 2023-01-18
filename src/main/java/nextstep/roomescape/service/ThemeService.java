package nextstep.roomescape.service;


import nextstep.roomescape.controller.RequestDTO.ThemeRequestDTO;
import nextstep.roomescape.repository.model.Theme;

import java.util.List;

public interface ThemeService {
    Long create(ThemeRequestDTO themeRequestDTO);

    List<Theme> findAll();

    void delete(long id);

    Theme find(Theme theme);

}
