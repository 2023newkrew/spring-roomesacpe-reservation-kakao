package roomescape.theme.repository;

import java.util.List;
import java.util.Optional;
import roomescape.entity.Theme;
import roomescape.theme.repository.dao.ThemeDao;
import roomescape.theme.repository.dao.ThemeDaoWithoutTemplateImpl;

public class ThemeRepositoryWithoutTemplateImpl implements ThemeRepository {
    private final ThemeDao themeDao = new ThemeDaoWithoutTemplateImpl();

    @Override
    public Long save(Theme theme) {
        return themeDao.create(theme);
    }

    @Override
    public Optional<Theme> findById(Long id) {
        return themeDao.selectById(id);
    }

    @Override
    public List<Theme> findAll() {
        return themeDao.selectAll();
    }

    @Override
    public int delete(Long id) {
        return themeDao.delete(id);
    }

    public boolean isThemeNameDuplicated(String themeName) {
        return themeDao.isThemeNameDuplicated(themeName);
    }
}
