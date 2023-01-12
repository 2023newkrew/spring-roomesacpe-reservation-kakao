package nextstep.service;

import nextstep.domain.Theme;
import nextstep.dto.request.CreateThemeRequest;
import nextstep.dto.response.ThemeResponse;
import nextstep.repository.theme.ThemeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<ThemeResponse> findAllThemes() {
        return themeRepository.findAll().stream()
                .map(ThemeResponse::ofEntity)
                .collect(Collectors.toList());
    }
}
