package roomescape.theme.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Theme;
import roomescape.theme.repository.ThemeRepository;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository){
        this.themeRepository = themeRepository;
    }

    public Long createTheme(Theme theme){
        //checkDuplicatedDateAndTime(reservation.getDate(), reservation.getTime());
        return themeRepository.save(theme);
    }

    public Theme findById(String themeId){
        return themeRepository.findById(themeId);
    }

    public void deleteById(String themeId){
        themeRepository.deleteById(themeId);
    }
}