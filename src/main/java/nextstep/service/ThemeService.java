package nextstep.service;

import nextstep.domain.theme.Theme;
import nextstep.domain.theme.repository.ThemeRepository;
import nextstep.dto.request.CreateThemeRequest;
import org.springframework.stereotype.Service;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Long createTheme(CreateThemeRequest createThemeRequest) {
        Theme savedTheme = themeRepository.save(new Theme(createThemeRequest.getName(), createThemeRequest.getDesc(), createThemeRequest.getPrice()));

        return savedTheme.getId();
    }

}
