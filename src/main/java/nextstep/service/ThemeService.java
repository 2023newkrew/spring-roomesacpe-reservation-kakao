package nextstep.service;

import java.util.List;

import nextstep.domain.Theme;
import nextstep.exceptions.ErrorCode;
import nextstep.exceptions.ReservationException;
import nextstep.exceptions.ThemeException;
import nextstep.repository.WebThemeDAO;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ThemeService {
    private final WebThemeDAO webThemeDAO;

    public ThemeService(WebThemeDAO webThemeDAO) {
        this.webThemeDAO = webThemeDAO;
    }

    public Long createTheme(Theme theme) {
        List<Theme> themesByName = webThemeDAO.findByName(theme.getName());
        if (themesByName.size() > 0) {
            throw new ThemeException(ErrorCode.ALREADY_THEME_EXISTS);
        }

        return webThemeDAO.insertWithKeyHolder(theme);
    }

    public List<Theme> lookupAllThemes() {
        return webThemeDAO.getAllThemes();
    }

    public Theme deleteTheme(Long id) {
        try {
            Theme theme = webThemeDAO.findById(id);
            webThemeDAO.delete(id);
            return theme;
        } catch(EmptyResultDataAccessException e) {
            throw new ThemeException(ErrorCode.THEME_NOT_FOUND);
        }
    }
}
