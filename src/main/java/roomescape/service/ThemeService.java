package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.repository.ThemeRepository;
import roomescape.domain.Theme;
import roomescape.dto.ThemeRequest;

@Service
@Transactional
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Theme createTheme(ThemeRequest themeRequest) {
        Long id =  themeRepository.createTheme(new Theme(
                null,
                themeRequest.getName(),
                themeRequest.getDesc(),
                themeRequest.getPrice()
                )
        );
        return themeRepository.findThemeById(id);
    }

    public Theme showTheme(Long id) {
        return themeRepository.findThemeById(id);
    }

    public Theme updateTheme(Theme theme) {
        themeRepository.updateTheme(theme);
        return themeRepository.findThemeById(theme.getId());
    }

    public int deleteTheme(Long id) {
        return themeRepository.deleteTheme(id);
    }
}
