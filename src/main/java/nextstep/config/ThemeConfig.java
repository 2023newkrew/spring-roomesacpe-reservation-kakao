package nextstep.config;

import lombok.RequiredArgsConstructor;
import nextstep.repository.ThemeRepository;
import nextstep.repository.ThemeRepositoryImpl;
import nextstep.service.ThemeService;
import nextstep.service.ThemeServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@RequiredArgsConstructor
public class ThemeConfig {

    private final JdbcTemplate jdbcTemplate;

    @Bean
    ThemeRepository themeRepository() {
        return new ThemeRepositoryImpl(jdbcTemplate);
    }

    @Bean
    ThemeService themeService() {
        return new ThemeServiceImpl(themeRepository());
    }
}
