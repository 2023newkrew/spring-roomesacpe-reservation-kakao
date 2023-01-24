package roomescape.theme.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Theme;
import roomescape.theme.dto.ThemeRequest;
import roomescape.theme.dto.ThemeResponse;
import roomescape.theme.exception.DuplicatedThemeException;
import roomescape.theme.exception.NoThemeException;
import roomescape.theme.repository.ThemeRepository;

import java.util.*;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Long createTheme(ThemeRequest themeRequest) {
        checkDuplicatedTheme(themeRequest.getName());
        return themeRepository.save(themeRequest);
    }

    private void checkDuplicatedTheme(String name) {
        Optional<Theme> duplicatedTheme = themeRepository.findByName(name);
        if (duplicatedTheme.isPresent()) {
            throw new DuplicatedThemeException("이미 존재하는 테마입니다.");
        }
    }

    public List<ThemeResponse> viewAll() {
        List<Theme> themes = themeRepository.viewAll();
        checkNoTheme(themes);
        List<ThemeResponse> themeResponses = themeToThemeResponse(themes);
        return Collections.unmodifiableList(themeResponses);
    }

    private void checkNoTheme(List<Theme> themes) {
        if (themes.isEmpty()) {
            throw new NoThemeException("테마 목록이 비어있습니다.");
        }
    }

    private List<ThemeResponse> themeToThemeResponse(List<Theme> themes) {
        List<ThemeResponse> themeResponses = new ArrayList<>();
        for (Theme theme : themes) {
            themeResponses.add(ThemeResponse.of(theme));
        }
        return themeResponses;
    }

    public void deleteById(String themeId) {
        themeRepository.deleteById(themeId);
    }
}