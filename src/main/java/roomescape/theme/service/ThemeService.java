package roomescape.theme.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import roomescape.entity.Theme;
import roomescape.exceptions.exception.DuplicatedThemeException;
import roomescape.exceptions.exception.NoThemesExistException;
import roomescape.exceptions.exception.ThemeNotFoundException;
import roomescape.theme.dto.ThemeRequestDto;
import roomescape.theme.dto.ThemeResponseDto;
import roomescape.theme.repository.ThemeRepository;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public long createTheme(ThemeRequestDto themeRequestDto) {
        Theme theme = themeRequestDto.toEntity();
        validateNonExistentTheme(theme.getName());
        return themeRepository.save(theme);
    }

    private void validateNonExistentTheme(String themeName) {
        if (themeRepository.isThemeNameDuplicated(themeName)) {
            throw new DuplicatedThemeException();
        }
    }

    public List<ThemeResponseDto> findAllThemes() {
        List<Theme> themes = themeRepository.findAll();
        if (themes.size() == 0) {
            throw new NoThemesExistException();
        }
        return themes.stream().map(ThemeResponseDto::of).collect(Collectors.toList());
    }

    public ThemeResponseDto findThemeById(Long themeId) {
        Theme theme = themeRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);
        return ThemeResponseDto.of(theme);
    }

    public void removeThemeById(Long themeId) {
        int removedThemeCount = themeRepository.delete(themeId);
        if (removedThemeCount == 0) {
            throw new ThemeNotFoundException();
        }
    }
}
