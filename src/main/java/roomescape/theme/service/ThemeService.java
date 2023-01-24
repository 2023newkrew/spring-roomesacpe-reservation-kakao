package roomescape.theme.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Theme;
import roomescape.theme.dto.ThemeRequest;
import roomescape.theme.dto.ThemeResponse;
import roomescape.theme.exception.DuplicatedThemeException;
import roomescape.theme.repository.ThemeRepository;

import java.util.*;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository){
        this.themeRepository = themeRepository;
    }

    public Long createTheme(ThemeRequest themeRequest){
        checkDuplicatedTheme(themeRequest.getName());
        return themeRepository.save(themeRequest);
    }

    private void checkDuplicatedTheme(String name) {
        Optional<Theme> duplicatedTheme = themeRepository.findByName(name);
        if (duplicatedTheme.isPresent()){
            throw new DuplicatedThemeException();
        }
    }

    public List<ThemeResponse> viewAll(){
        List<Theme> themes = themeRepository.viewAll();
        List<ThemeResponse> themeResponses = themeToThemeResponse(themes);
        return Collections.unmodifiableList(themeResponses);
    }

    private List<ThemeResponse> themeToThemeResponse(List<Theme> themes) {
        List<ThemeResponse> themeResponses = new ArrayList<>();
        for ( Theme theme : themes ){
            themeResponses.add(ThemeResponse.of(theme));
        }
        return themeResponses;
    }

    public void deleteById(String themeId){
        themeRepository.deleteById(themeId);
    }
}