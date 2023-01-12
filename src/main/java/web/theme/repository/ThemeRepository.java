package web.theme.repository;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import web.entity.Theme;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class ThemeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ThemeRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public long save(Theme theme) {
        return 0;
    }

    public Optional<Theme> findById(long themeId) {
        return Optional.empty();
    }

    public Long delete(long themeId) {
        return null;
    }

    public void clearAll() {

    }
}
