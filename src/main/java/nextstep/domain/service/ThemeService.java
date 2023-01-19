package nextstep.domain.service;

import nextstep.domain.Theme;
import nextstep.domain.dto.ThemeRequest;
import nextstep.domain.dto.ThemeResponse;

import java.util.List;

public interface ThemeService {
    Theme newTheme(ThemeRequest themeRequest);

    ThemeResponse findTheme(long id);

    List<ThemeResponse> findAllTheme();

    boolean deleteTheme(long id);
}
