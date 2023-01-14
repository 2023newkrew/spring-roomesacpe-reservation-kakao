package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.controller.dto.ThemeRequest;
import roomescape.controller.dto.ThemeResponse;
import roomescape.domain.Theme;
import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ThemeRepository;

import java.util.List;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;
    private final ReservationRepository reservationRepository;

    public ThemeService(ThemeRepository themeRepository, ReservationRepository reservationRepository) {
        this.themeRepository = themeRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<ThemeResponse> getAllThemes() {
        List<Theme> themes = themeRepository.findAllThemes();
        return ThemeResponse.toList(themes);
    }

    public ThemeResponse getTheme(Long themeId) {
        Theme theme = themeRepository.findThemeById(themeId)
                .orElseThrow(() -> new RoomEscapeException(ErrorCode.THEME_NOT_FOUND));
        return ThemeResponse.of(theme);
    }

    @Transactional
    public Long createTheme(ThemeRequest themeRequest) {
        themeRepository.findThemeByName(themeRequest.getName())
                .ifPresent(theme -> {
                    throw new RoomEscapeException(ErrorCode.DUPLICATED_THEME_NAME);
                });
        Theme theme = themeRequest.toEntity();
        return themeRepository.insertTheme(theme);
    }

    @Transactional
    public void changeTheme(Long themeId, ThemeRequest themeRequest) {
        Theme theme = themeRepository.findThemeById(themeId)
                .orElseThrow(() -> new RoomEscapeException(ErrorCode.THEME_NOT_FOUND));
        themeRepository.changeTheme(
                theme.getId(),
                themeRequest.getName(),
                themeRequest.getDesc(),
                themeRequest.getPrice()
        );
    }

    @Transactional
    public void deleteTheme(Long themeId) {
        Theme theme = themeRepository.findThemeById(themeId)
                .orElseThrow(() -> new RoomEscapeException(ErrorCode.THEME_NOT_FOUND));
        reservationRepository.deleteReservationByThemeId(theme.getId());
        themeRepository.deleteTheme(theme.getId());
    }
}
