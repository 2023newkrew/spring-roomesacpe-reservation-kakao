package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ThemeRequestDto;
import roomescape.dto.ThemeResponseDto;
import roomescape.dto.ThemesResponseDto;
import roomescape.model.Theme;
import roomescape.repository.ThemeJdbcRepository;
import roomescape.repository.ThemeRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;

    public ThemeService(ThemeJdbcRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public ThemeResponseDto createTheme(ThemeRequestDto req) {
        if (themeRepository.has(req.getName())) {
            throw new IllegalArgumentException("Already have theme at that name");
        }
        Theme theme = new Theme(req);
        Long id = themeRepository.save(theme);
        theme.setId(id);
        return new ThemeResponseDto(theme);
    }

    public ThemesResponseDto findThemes() {
        List<Theme> themes = getThemes();
        return new ThemesResponseDto(themes);
    }

    public Boolean deleteTheme(Long id) {
        return themeRepository.delete(id) == 1;
    }

    Theme getTheme(Long id) {
        return themeRepository.find(id).orElseThrow(() -> {
            throw new NoSuchElementException("No Theme by that ID");
        });
    }

    List<Theme> getThemes() {
        return themeRepository.findAll();
    }
}
