package nextstep.service;

import nextstep.dao.ThemeDAO;
import nextstep.domain.Theme;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {
    private final ThemeDAO themeDAO;

    public ThemeService(ThemeDAO themeJdbcTemplateDAO) {
        this.themeDAO = themeJdbcTemplateDAO;
    }

    public Long saveTheme(Theme theme) {
        return themeDAO.save(theme);
    }

    public List<Theme> findAllTheme() {
        return themeDAO.findAll();
    }

    public void deleteTheme(Long id) {
        themeDAO.deleteById(id);
    }
}
