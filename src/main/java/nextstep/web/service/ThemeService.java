package nextstep.web.service;

import java.util.List;

import nextstep.domain.Theme;
import nextstep.web.exceptions.ErrorCode;
import nextstep.web.exceptions.ThemeException;
import nextstep.web.repository.ThemeDAOImpl;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ThemeService {
    private final ThemeDAOImpl themeDAOImpl;

    public ThemeService(ThemeDAOImpl themeDAOImpl) {
        this.themeDAOImpl = themeDAOImpl;
    }

    public Long createTheme(Theme theme) {
        List<Theme> themesByName = themeDAOImpl.findByName(theme.getName());
        if (themesByName.size() > 0) {
            throw new ThemeException(ErrorCode.ALREADY_THEME_EXISTS);
        }

        return themeDAOImpl.insertWithKeyHolder(theme);
    }

    public List<Theme> lookupAllThemes() {
        return themeDAOImpl.getAllThemes();
    }

    public Theme deleteTheme(Long id) {
        try {
            Theme theme = themeDAOImpl.findById(id);
            themeDAOImpl.delete(id);
            return theme;
        } catch(EmptyResultDataAccessException e) {
            throw new ThemeException(ErrorCode.THEME_NOT_FOUND);
        }
    }
}
