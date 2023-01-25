package roomescape.theme.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Theme;
import roomescape.globalexception.NoSuchThemeException;
import roomescape.reservation.exception.DuplicatedReservationException;
import roomescape.reservation.exception.NoSuchReservationException;
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
        themeRepository.findByName(name)
                .ifPresent(s -> {
                    throw new DuplicatedThemeException();
                });
    }

    public List<ThemeResponse> viewAll() {
        List<Theme> themes = themeRepository.viewAll();
        checkNoTheme(themes);
        List<ThemeResponse> themeResponses = themeToThemeResponse(themes);
        return Collections.unmodifiableList(themeResponses);
    }

    private void checkNoTheme(List<Theme> themes) {
        if (themes.isEmpty()) {
            throw new NoThemeException();
        }
    }

    private List<ThemeResponse> themeToThemeResponse(List<Theme> themes) {
        List<ThemeResponse> themeResponses = new ArrayList<>();
        for (Theme theme : themes) {
            themeResponses.add(ThemeResponse.of(theme));
        }
        return themeResponses;
    }

    public void deleteById(Long themeId) {
        checkExistenceOfTheme(themeRepository.findById(themeId));
        themeRepository.deleteById(themeId);
    }

    private void checkExistenceOfTheme(Optional<Theme> theme) {
        theme.orElseThrow(
                () -> new NoSuchThemeException("해당 id를 가진 테마가 존재하지 않습니다.")
        );
    }
}