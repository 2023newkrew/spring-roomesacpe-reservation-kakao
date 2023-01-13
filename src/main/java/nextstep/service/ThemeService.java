package nextstep.service;

import java.util.List;
import nextstep.model.Theme;
import nextstep.repository.ThemeRepository;
import nextstep.web.dto.ThemeRequest;
import org.springframework.stereotype.Service;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Theme save(ThemeRequest request) {
        Theme theme = new Theme(request.getName(), request.getDesc(), request.getPrice());
        return themeRepository.save(theme);
    }

    public List<Theme> getThemes() {
        return themeRepository.findAll();
    }
}