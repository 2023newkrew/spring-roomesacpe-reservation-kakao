package nextstep.service;

import nextstep.domain.Theme;
import nextstep.dto.ThemeDto;
import nextstep.repository.ThemeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public void add(ThemeDto request) {
        Theme theme = request.toTheme();
        themeRepository.add(theme);
    }

    public Theme findById(Long id) { return themeRepository.findById(id); }

    public List<Theme> findAll() { return themeRepository.findAll(); }

    public void update(Long id, ThemeDto request) {
        Theme theme = request.toTheme();
        themeRepository.update(id, theme);
    }

    public void deleteById(Long id) { themeRepository.deleteById(id); }

}
