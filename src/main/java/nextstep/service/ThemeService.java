package nextstep.service;

import nextstep.domain.Theme;
import nextstep.dto.ThemeDto;
import nextstep.exception.ThemeReservedException;
import nextstep.repository.ReservationRepository;
import nextstep.repository.ThemeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;
    private final ReservationRepository reservationRepository;

    public ThemeService(ThemeRepository themeRepository, ReservationRepository reservationRepository) {
        this.themeRepository = themeRepository;
        this.reservationRepository = reservationRepository;
    }

    public void save(ThemeDto request) {
        Theme theme = request.toTheme();
        themeRepository.save(theme);
    }

    public Theme findById(Long id) { return themeRepository.findById(id); }

    public List<Theme> findAll() { return themeRepository.findAll(); }

    public void update(Long id, ThemeDto request) {
        Theme theme = request.toTheme();
        themeRepository.update(id, theme);
    }

    public void deleteById(Long id) {
        if (reservationRepository.existsByThemeId(id)) {
            throw new ThemeReservedException();
        }
        themeRepository.deleteById(id);
    }

}
