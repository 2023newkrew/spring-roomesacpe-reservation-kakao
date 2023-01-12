package nextstep.service;

import nextstep.domain.theme.Theme;
import nextstep.domain.theme.repository.ThemeRepository;
import nextstep.dto.request.CreateThemeRequest;
import nextstep.error.ApplicationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static nextstep.error.ErrorType.DUPLICATE_THEME;
import static nextstep.error.ErrorType.THEME_REFERENTIAL_ERROR;

@Service
public class ThemeWriteService {

    private final ThemeRepository themeRepository;
    private final ReservationReadService reservationReadService;

    public ThemeWriteService(ThemeRepository themeRepository, ReservationReadService reservationReadService) {
        this.themeRepository = themeRepository;
        this.reservationReadService = reservationReadService;
    }

    @Transactional
    public Long createTheme(CreateThemeRequest createThemeRequest) {
        if (themeRepository.findByName(createThemeRequest.getName()).isPresent()) {
            throw new ApplicationException(DUPLICATE_THEME);
        }

        Theme savedTheme = themeRepository.save(new Theme(createThemeRequest.getName(), createThemeRequest.getDesc(), createThemeRequest.getPrice()));
        return savedTheme.getId();
    }

    @Transactional
    public void deleteThemeById(Long themeId) {
        if (reservationReadService.existsByThemeId(themeId)) {
            throw new ApplicationException(THEME_REFERENTIAL_ERROR);
        }

        themeRepository.deleteById(themeId);
    }

}
