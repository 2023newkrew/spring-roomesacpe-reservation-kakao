package nextstep.web.repository;

import java.util.List;
import nextstep.domain.Theme;
import org.springframework.stereotype.Repository;

@Repository
public class ThemeRepositoryImpl implements ThemeRepository {
    private final ThemeDAOImpl themeDAOImpl;

    public ThemeRepositoryImpl(ThemeDAOImpl themeDAOImpl) {
        this.themeDAOImpl = themeDAOImpl;
    }

    @Override
    public Long insertWithKeyHolder(Theme theme) {
        return themeDAOImpl.insertWithKeyHolder(theme);
    }

    @Override
    public Theme findById(Long id) {
        return themeDAOImpl.findById(id);
    }

    @Override
    public List<Theme> findByName(String name) {
        return themeDAOImpl.findByName(name);
    }

    @Override
    public List<Theme> getAllThemes() {
        return themeDAOImpl.getAllThemes();
    }

    @Override
    public Integer delete(Long id) {
        return themeDAOImpl.delete(id);
    }
}
