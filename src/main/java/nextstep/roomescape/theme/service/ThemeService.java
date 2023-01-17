package nextstep.roomescape.theme.service;


import nextstep.roomescape.theme.controller.dto.ThemeRequestDTO;
import nextstep.roomescape.theme.repository.model.Theme;

import java.util.List;

public interface ThemeService {
    Long create(ThemeRequestDTO themeRequestDTO);

    List<Theme> findAll();

    void delete(long id);

}
