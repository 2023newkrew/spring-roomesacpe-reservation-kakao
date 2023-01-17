package nextstep.domain;

import nextstep.domain.repository.ReservationRepository;
import nextstep.domain.repository.ThemeRepository;
import nextstep.dto.CreateThemeRequest;
import nextstep.dto.ThemeResponse;
import nextstep.dto.ThemesResponse;
import nextstep.exception.DuplicateThemeException;
import nextstep.exception.ReservedThemeDeleteException;
import nextstep.exception.ThemeNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class ThemeManager {
    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;

    public ThemeManager(ReservationRepository reservationRepository, ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.themeRepository = themeRepository;
    }

    public Long createTheme(CreateThemeRequest themeRequest){
        if(themeRepository.existByThemeName(themeRequest.getName()))
            throw new DuplicateThemeException();

        return themeRepository.save(new Theme(themeRequest.getName(), themeRequest.getDesc(), themeRequest.getPrice()));
    }

    public ThemeResponse findThemeById(Long id) {
        Theme theme = themeRepository.findThemeById(id).orElseThrow(ThemeNotFoundException::new);
        return ThemeResponse.from(theme);
    }

    public ThemesResponse getAllThemes() {
        List<Theme> themes = themeRepository.getAllThemes();
        return new ThemesResponse(
                themes.stream()
                        .map(ThemeResponse::from)
                        .collect(Collectors.toList())
        );
    }

    public boolean deleteThemeById(Long id) {
        if(reservationRepository.findByThemeId(id).size() > 0)
            throw new ReservedThemeDeleteException();

        return themeRepository.deleteThemeById(id);
    }
}
