package reservation.service;

import org.springframework.stereotype.Service;
import reservation.domain.Theme;
import reservation.domain.dto.ThemeDto;
import reservation.handler.exception.DuplicatedException;
import reservation.respository.ThemeRepository;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Long createTheme(ThemeDto themeDto) {
        if (themeRepository.existTheme(themeDto)) {
            throw new DuplicatedException();
        }
        return themeRepository.createTheme(themeDto);
    }

    public Theme getTheme(Long themeId) {
        return themeRepository.getTheme(themeId);
    }

    public void deleteTheme(Long themeId) {
        themeRepository.deleteTheme(themeId);
    }
}
