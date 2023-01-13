package nextstep.service;

import nextstep.domain.Theme;
import nextstep.domain.repository.ReservationRepository;
import nextstep.domain.repository.ThemeRepository;
import nextstep.dto.CreateThemeRequest;
import nextstep.dto.ThemeResponse;
import nextstep.dto.ThemesResponse;
import nextstep.exception.DuplicateThemeException;
import nextstep.exception.ReservedThemeDeleteException;
import nextstep.exception.ThemeNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;
    private final ReservationRepository reservationRepository;

    public ThemeService(ThemeRepository themeRepository, ReservationRepository reservationRepository){
        this.themeRepository = themeRepository;
        this.reservationRepository = reservationRepository;
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
                        .map(theme -> ThemeResponse.from(theme))
                        .collect(Collectors.toList())
        );
    }

    public boolean deleteThemeById(Long id) {
        System.out.println(id);
        if(reservationRepository.findByThemeId(id).size() > 0)
            throw new ReservedThemeDeleteException();

        return themeRepository.deleteThemeById(id);
    }
}
