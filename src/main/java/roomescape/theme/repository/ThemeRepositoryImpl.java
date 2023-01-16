package roomescape.theme.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.entity.Theme;
import roomescape.theme.repository.dao.ThemeDao;
import roomescape.theme.repository.dao.ThemeDaoImpl;

@Repository
public class ThemeRepositoryImpl implements ThemeRepository {
    private final ThemeDao themeDao;

    public ThemeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.themeDao = new ThemeDaoImpl(jdbcTemplate);
    }

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
