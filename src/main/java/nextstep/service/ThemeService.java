package nextstep.service;

import nextstep.domain.Theme;
import nextstep.domain.repository.ThemeRepository;
import nextstep.dto.CreateThemeRequest;
import nextstep.dto.ThemeResponse;
import nextstep.dto.ThemesResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository){
        this.themeRepository = themeRepository;
    }

    public Long createTheme(CreateThemeRequest themeRequest){
        return themeRepository.save(new Theme(themeRequest.getName(), themeRequest.getDesc(), themeRequest.getPrice()));
    }

    public ThemeResponse findThemeById(Long id) {
        Theme theme = themeRepository.findThemeById(id).orElseThrow(RuntimeException::new);
        return ThemeResponse.from(theme);
    }

    public ThemesResponse getAllThemes() {
        List<Theme> themes = themeRepository.getAllThemes();
        return new ThemesResponse(
                themes.stream()
                        .map(theme -> ThemeResponse.from(theme))
                        .collect(Collectors.toList())
        );
    }
}
