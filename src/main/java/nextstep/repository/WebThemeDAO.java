package nextstep.repository;

import java.util.List;
import nextstep.domain.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WebThemeDAO implements ThemeDAO{
    private JdbcTemplate jdbcTemplate;

    public WebThemeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long insertWithKeyHolder(Theme theme) {
        return null;
    }

    @Override
    public Theme findById(Long id) {
        return null;
    }

    @Override
    public Theme findByName(String name) {
        return null;
    }

    @Override
    public List<Theme> getAllThemes() {
        return null;
    }

    @Override
    public Integer delete(Long id) {
        return null;
    }
}
