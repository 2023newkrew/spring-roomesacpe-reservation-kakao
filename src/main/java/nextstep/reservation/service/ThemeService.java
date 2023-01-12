package nextstep.reservation.service;

import nextstep.reservation.entity.Theme;
import nextstep.reservation.repository.ThemeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Theme create(Theme theme) {
        return themeRepository.create(theme);
    }

    public List<Theme> findAll() {
        return themeRepository.findAll();
    }

    public void deleteById(Long id) {
        themeRepository.deleteById(id);
    }

    public void clear() {
        themeRepository.clear();
    }
}
