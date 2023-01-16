package reservation.service;

import org.springframework.stereotype.Service;
import reservation.domain.Theme;
import reservation.handler.exception.DuplicatedObjectException;
import reservation.respository.ThemeRepository;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Long createTheme(Theme theme) {
        if (themeRepository.existTheme(theme)) {
            throw new DuplicatedObjectException();
        }
        return themeRepository.createTheme(theme);
    }

    public Theme getTheme(long themeId) {
        return themeRepository.getTheme(themeId);
    }

    public void deleteTheme(long themeId) {
        themeRepository.deleteTheme(themeId);
    }
}
