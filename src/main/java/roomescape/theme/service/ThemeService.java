package roomescape.theme.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Theme;
import roomescape.theme.repository.ThemeRepository;

import java.util.List;

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

    public List<Theme> viewAll(){
        return themeRepository.viewAll();
    }

    public void deleteById(String themeId){
        themeRepository.deleteById(themeId);
    }
}