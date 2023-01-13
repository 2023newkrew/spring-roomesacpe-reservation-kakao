package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ThemeRequestDto;
import roomescape.dto.ThemeResponseDto;
import roomescape.dto.ThemeUpdateRequestDto;
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
        Theme theme = findThemeByIdOrThrowException(themeId);
        return new ThemeResponseDto(theme);
    }

    public void updateTheme(Long themeId, ThemeUpdateRequestDto themeUpdateRequestDto) {
        findThemeByIdOrThrowException(themeId);
        if (themeUpdateRequestDto.getName() != null) {
            themeRepository.updateNameOfId(themeId, themeUpdateRequestDto.getName());
        }
        if (themeUpdateRequestDto.getDesc() != null) {
            themeRepository.updateDescOfId(themeId, themeUpdateRequestDto.getDesc());
        }
        if (themeUpdateRequestDto.getPrice() != null) {
            themeRepository.updatePriceOfId(themeId, themeUpdateRequestDto.getPrice());
        }
    }

    public void deleteTheme(Long themeId) {
        checkForReservationsForTheme(themeId);
        themeRepository.delete(themeId);
    }

    private Theme findThemeByIdOrThrowException(Long themeId) {
        return themeRepository.findOneById(themeId).orElseThrow(() -> new NoSuchElementException("No Theme by that Id"));
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
