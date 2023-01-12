package nextstep.service;

import nextstep.dto.ThemeRequest;
import nextstep.exception.ThemeNotFoundException;
import nextstep.model.Theme;
import nextstep.web.JdbcTemplateThemeRepository;
import org.springframework.stereotype.Service;

@Service
public class ThemeService {

    private final JdbcTemplateThemeRepository themeRepository;

    public ThemeService(JdbcTemplateThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Theme createTheme(ThemeRequest request) {
        return themeRepository.save(new Theme(null, request.getName(), request.getDesc(), request.getPrice()));
    }

    public Theme getTheme(Long id) {
        return themeRepository.findById(id)
                .orElseThrow(() -> new ThemeNotFoundException(id));
    }

    public void deleteTheme(Long id) {
        themeRepository.deleteById(id);
    }
}