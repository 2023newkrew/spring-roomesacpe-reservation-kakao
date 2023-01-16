package web.service;

import org.springframework.stereotype.Service;
import web.domain.Theme;
import web.dto.request.ThemeRequestDTO;
import web.dto.response.ThemeIdDto;
import web.dto.response.ThemeResponseDTO;
import web.exception.DuplicatedThemeException;
import web.repository.ThemeJdbcRepository;

import java.util.List;

@Service
public class ThemeService {
    private final ThemeJdbcRepository themeJdbcRepository;

    public ThemeService(ThemeJdbcRepository themeJdbcRepository) {
        this.themeJdbcRepository = themeJdbcRepository;
    }

    public ThemeIdDto createTheme(ThemeRequestDTO themeRequestDTO) {
        Theme theme  = themeRequestDTO.toEntity();

        validateThemeDuplicated(theme);

        return themeJdbcRepository.createTheme(theme);
    }

    private void validateThemeDuplicated(Theme theme) {

        List<Long> ids = themeJdbcRepository.findAllThemeByName(theme);

        System.out.println(ids);

        if (ids.size() > 0) {
            throw new DuplicatedThemeException();
        }
    }

    public List<ThemeResponseDTO> findAllThemes() {
        return themeJdbcRepository.findAllThemes();
    }

    public void updateTheme(Long themeId, ThemeRequestDTO themeRequestDTO) {
        themeJdbcRepository.findThemeById(themeId);
        themeJdbcRepository.updateTheme(themeId, themeRequestDTO);
    }

    public void deleteThemeById(Long themeId) {
        themeJdbcRepository.findThemeById(themeId);
        themeJdbcRepository.deleteTheme(themeId);
    }
}
