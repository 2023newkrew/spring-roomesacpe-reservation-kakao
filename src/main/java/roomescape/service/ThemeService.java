package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ThemeRequestDto;
import roomescape.dto.ThemeResponseDto;
import roomescape.model.Theme;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ThemeRepository;

import java.util.NoSuchElementException;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;
    private final ReservationRepository reservationRepository;

    public ThemeService(ThemeRepository themeRepository, ReservationRepository reservationRepository) {
        this.themeRepository = themeRepository;
        this.reservationRepository = reservationRepository;
    }

    public Long createTheme(ThemeRequestDto themeRequest) {
        checkForSameNameTheme(themeRequest);
        Theme theme = themeRequest.toEntity();
        return themeRepository.save(theme);
    }

    public ThemeResponseDto findTheme(Long themeId) {
        Theme theme = themeRepository
                .findOneById(themeId)
                .orElseThrow(() -> {
                    throw new NoSuchElementException("No Theme by that Id");});
        return new ThemeResponseDto(theme);
    }

    public void deleteTheme(Long themeId) {
        checkForReservationsForTheme(themeId);
        themeRepository.delete(themeId);
    }

    private void checkForSameNameTheme(ThemeRequestDto themeRequest) {
        if (themeRepository.hasThemeWithName(themeRequest.getName())) {
            throw new IllegalArgumentException("Already have theme with same name");
        }
    }

    private void checkForReservationsForTheme(Long themeId) {
        if (reservationRepository.hasReservationOfTheme(themeId)) {
            throw new IllegalArgumentException("There are existing reservations for that theme");
        }
    }
}
