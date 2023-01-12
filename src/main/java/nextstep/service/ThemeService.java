package nextstep.service;

import nextstep.dao.ThemeDAO;
import nextstep.domain.Theme;
import nextstep.exceptions.DataNotExistException;
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
        if (themeDAO.deleteById(id) == 0) {
            throw new DataNotExistException("테마가 존재하지 않습니다.");
        }
    }
}
