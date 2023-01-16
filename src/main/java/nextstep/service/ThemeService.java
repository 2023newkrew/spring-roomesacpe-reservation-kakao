package nextstep.service;

import nextstep.dto.ThemeRequest;
import nextstep.exception.ThemeDuplicateException;
import nextstep.exception.ThemeNotFoundException;
import nextstep.exception.ThemeReservationExistsException;
import nextstep.model.Reservation;
import nextstep.model.Theme;
import nextstep.web.JdbcTemplateThemeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThemeService {

    private final JdbcTemplateThemeRepository themeRepository;

    public ThemeService(JdbcTemplateThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Theme createTheme(ThemeRequest request) {
        if (existsThemeByName(request.getName())) {
            throw new ThemeDuplicateException();
        }
        return themeRepository.save(new Theme(null, request.getName(), request.getDesc(), request.getPrice()));
    }

    private Boolean existsThemeByName(String name) {
        Optional<Theme> optional =  themeRepository.findByName(name);
        return optional.isPresent();
    }

    public Theme getTheme(Long id) {
        return themeRepository.findById(id)
                .orElseThrow(() -> new ThemeNotFoundException(id));
    }

    public void deleteTheme(Long id, List<Reservation> reservations) { // 다른 서비스에서 엔티티 사용??
        if (reservations.size() != 0) {
            throw new ThemeReservationExistsException(id);
        }
        themeRepository.deleteById(id);
    }
}