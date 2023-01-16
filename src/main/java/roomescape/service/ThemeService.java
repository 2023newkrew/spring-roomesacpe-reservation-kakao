package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.ThemeRequestDto;
import roomescape.dto.ThemeResponseDto;
import roomescape.dto.ThemeUpdateRequestDto;
import roomescape.dto.ThemesResponseDto;
import roomescape.model.Theme;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ThemeRepository;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ThemeService {
    private final ThemeRepository themeRepository;
    private final ReservationRepository reservationRepository;

    public ThemeService(ThemeRepository themeRepository, ReservationRepository reservationRepository) {
        this.themeRepository = themeRepository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public Long createTheme(ThemeRequestDto themeRequest) {
        checkForSameNameTheme(themeRequest);
        Theme theme = themeRequest.toEntity();
        return themeRepository.save(theme);
    }

    public ThemeResponseDto findTheme(Long themeId) {
        Theme theme = findThemeByIdOrThrowException(themeId);
        return new ThemeResponseDto(theme);
    }

    public ThemesResponseDto findAllTheme() {
        return new ThemesResponseDto(themeRepository.findAll().stream().map(ThemeResponseDto::new).collect(Collectors.toList()));
    }

    @Transactional
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

    @Transactional
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
