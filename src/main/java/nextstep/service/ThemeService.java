package nextstep.service;

import nextstep.domain.Theme;
import nextstep.dto.request.CreateThemeRequest;
import nextstep.dto.response.ThemeResponse;
import nextstep.repository.ThemeRepository;
import org.springframework.stereotype.Service;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public ThemeResponse createTheme(CreateThemeRequest createThemeRequest) {
        Theme theme = createThemeRequest.toEntity();
        return ThemeResponse.ofEntity(themeRepository.add(theme));
    }
}
