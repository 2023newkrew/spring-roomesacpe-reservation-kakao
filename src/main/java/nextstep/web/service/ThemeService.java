package nextstep.web.service;

import nextstep.domain.ThemeManager;
import nextstep.domain.repository.ReservationRepository;
import nextstep.domain.repository.ThemeRepository;
import nextstep.dto.CreateThemeRequest;
import nextstep.dto.ThemeResponse;
import nextstep.dto.ThemesResponse;
import org.springframework.stereotype.Service;

@Service
public class ThemeService {

    private final ThemeManager themeManager;

    public ThemeService(ThemeRepository themeRepository, ReservationRepository reservationRepository){
        themeManager = new ThemeManager(reservationRepository, themeRepository);
    }

    public Long createTheme(CreateThemeRequest themeRequest){
        return themeManager.createTheme(themeRequest);
    }

    public ThemeResponse findThemeById(Long id) {
        return themeManager.findThemeById(id);
    }

    public ThemesResponse getAllThemes() {
        return themeManager.getAllThemes();
    }

    public boolean deleteThemeById(Long id) {
        return themeManager.deleteThemeById(id);
    }
}
