package nextstep.service;

import nextstep.domain.Theme;
import nextstep.domain.repository.ThemeRepository;
import nextstep.dto.CreateThemeRequest;
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
}
