package web.service;

import org.springframework.stereotype.Service;
import web.domain.Reservation;
import web.domain.Theme;
import web.dto.request.ThemeRequestDTO;
import web.dto.response.ThemeIdDto;
import web.dto.response.ThemeResponseDTO;
import web.exception.DuplicatedThemeException;
import web.repository.ReservationJdbcRepository;
import web.repository.ThemeJdbcRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ThemeService {
    private final ThemeJdbcRepository themeJdbcRepository;

    private final ReservationJdbcRepository reservationJdbcRepository;

    public ThemeService(ThemeJdbcRepository themeJdbcRepository, ReservationJdbcRepository reservationJdbcRepository) {
        this.themeJdbcRepository = themeJdbcRepository;
        this.reservationJdbcRepository = reservationJdbcRepository;
    }

    public ThemeIdDto createTheme(ThemeRequestDTO themeRequestDTO) {
        Theme theme  = themeRequestDTO.toEntity();

        validateThemeDuplicated(theme);

        return themeJdbcRepository.createTheme(theme);
    }

    private void validateThemeDuplicated(Theme theme) {

        List<Long> ids = themeJdbcRepository.findAllThemeByName(theme);

        System.out.println(ids);

        if (ids.size() > 0) {
            throw new DuplicatedThemeException();
        }
    }

    public List<ThemeResponseDTO> findAllThemes() {
        return themeJdbcRepository.findAllThemes();
    }

    public void updateTheme(Long themeId, ThemeRequestDTO themeRequestDTO) {
        themeJdbcRepository.findThemeById(themeId);
        themeJdbcRepository.updateTheme(themeId, themeRequestDTO);
    }

    public void deleteThemeById(Long themeId) {
        themeJdbcRepository.findThemeById(themeId);
        Optional<Reservation> reservation = reservationJdbcRepository.findReservationByThemId(themeId);
        if (reservation.isPresent()) {
            throw new DuplicatedThemeException();
        }
        themeJdbcRepository.deleteTheme(themeId);
    }
}
