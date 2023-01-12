package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.model.Theme;
import roomescape.repository.ThemeRepository;

import java.util.NoSuchElementException;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    Theme getTheme(Long id) {
        return themeRepository
                .find(id)
                .orElseThrow(() -> {throw new NoSuchElementException("No Theme by that ID");});
    }
}
