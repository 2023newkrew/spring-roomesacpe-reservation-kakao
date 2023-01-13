package nextstep.config;

import javax.sql.DataSource;
import nextstep.repository.ThemeRepository;
import nextstep.repository.ThemeRepositoryImpl;
import nextstep.service.ThemeService;
import nextstep.service.ThemeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class ThemeConfig {

    private final JdbcTemplate jdbcTemplate;

    public ThemeConfig(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Bean
    ThemeRepository themeRepository() {
        return new ThemeRepositoryImpl(jdbcTemplate);
    }

    @Bean
    ThemeService themeService() {
        return new ThemeServiceImpl(themeRepository());
    }
}
