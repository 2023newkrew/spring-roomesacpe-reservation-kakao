package roomescape.theme.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Theme;
import roomescape.theme.dto.ThemeRequest;
import roomescape.theme.dto.ThemeResponse;
import roomescape.theme.repository.ThemeRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository){
        this.themeRepository = themeRepository;
    }

    public Long createTheme(ThemeRequest themeRequest){
        //checkDuplicatedDateAndTime(reservation.getDate(), reservation.getTime());
        return themeRepository.save(themeRequest);
    }

    public List<ThemeResponse> viewAll(){
        List<Theme> themes = themeRepository.viewAll();
        List<ThemeResponse> themeResponses = new ArrayList<>();
        for ( Theme theme : themes ){
            themeResponses.add(ThemeResponse.of(theme));
        }
        return Collections.unmodifiableList(themeResponses);
    }

    public void deleteById(String themeId){
        themeRepository.deleteById(themeId);
    }
}