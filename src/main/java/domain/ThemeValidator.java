package domain;

import kakao.error.exception.DuplicatedThemeException;
import kakao.error.exception.UsingThemeException;
import kakao.repository.ReservationRepository;
import kakao.repository.ThemeRepository;

public class ThemeValidator {
    private final ThemeRepository themeRepository;
    private final ReservationRepository reservationRepository;

    public ThemeValidator(ThemeRepository themeRepository, ReservationRepository reservationRepository) {
        this.themeRepository = themeRepository;
        this.reservationRepository = reservationRepository;
    }

    public void validateForSameName(String name) {
        if (!themeRepository.findByName(name).isEmpty()) throw new DuplicatedThemeException();
    }

    public void validateForUsingTheme(Long themeId) {
        if (!reservationRepository.findByThemeId(themeId).isEmpty()) throw new UsingThemeException();
    }
}
