package nextstep.service;

import nextstep.domain.Theme;
import nextstep.dto.web.request.CreateThemeRequest;
import nextstep.dto.web.response.ThemeResponse;
import nextstep.exception.ReservedThemeException;
import nextstep.repository.reservation.ReservationRepository;
import nextstep.repository.theme.ThemeRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;
    private final ReservationRepository reservationRepository;

    public ThemeService(ThemeRepository themeRepository, @Qualifier("jdbcTemplate") ReservationRepository reservationRepository) {
        this.themeRepository = themeRepository;
        this.reservationRepository = reservationRepository;
    }

    public ThemeResponse createTheme(CreateThemeRequest createThemeRequest) {
        Theme theme = createThemeRequest.toEntity();
        return ThemeResponse.ofEntity(themeRepository.add(theme));
    }

    public List<ThemeResponse> findAllThemes() {
        return themeRepository.findAll().stream()
                .map(ThemeResponse::ofEntity)
                .collect(Collectors.toList());
    }

    public void deleteAllThemes() {
        themeRepository.deleteAll();
    }

    public void deleteTheme(Long id) throws ReservedThemeException {
        if (reservationRepository.hasReservationWithTheme(id)) {
            throw new ReservedThemeException();
        }

        themeRepository.delete(id);
    }
}
