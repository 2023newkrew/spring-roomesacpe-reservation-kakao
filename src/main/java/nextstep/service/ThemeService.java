package nextstep.service;

import nextstep.domain.Theme;
import nextstep.domain.repository.ThemeRepository;
import nextstep.dto.CreateThemeRequest;
import nextstep.dto.ThemeResponse;
import org.springframework.stereotype.Service;

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
}
