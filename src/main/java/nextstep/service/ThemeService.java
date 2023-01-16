package nextstep.service;

import nextstep.domain.dto.theme.CreateThemeDto;
import nextstep.domain.dto.theme.UpdateThemeDto;
import nextstep.domain.theme.Theme;
import nextstep.repository.theme.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    @Autowired
    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public List<Theme> getAllThemes() {
        return themeRepository.findAll();
    }

    public long addTheme(CreateThemeDto createThemeDto) {
        return themeRepository.add(Theme.createTheme(createThemeDto));
    }

    public void updateTheme(UpdateThemeDto updateThemeDto) {
        themeRepository.update(Theme.createTheme(updateThemeDto));
    }

    public void deleteTheme(Long id) {
        themeRepository.delete(id);
    }
}
