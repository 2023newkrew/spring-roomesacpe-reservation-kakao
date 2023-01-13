package nextstep.config;

import javax.sql.DataSource;
import nextstep.repository.ThemeRepository;
import nextstep.repository.ThemeRepositoryImpl;
import nextstep.service.ThemeService;
import nextstep.service.ThemeServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThemeConfig {
    @Bean
    ThemeRepository themeRepository(DataSource dataSource){
        return new ThemeRepositoryImpl(dataSource);
    }

    @Bean
    ThemeService themeService(DataSource dataSource){
        return new ThemeServiceImpl(themeRepository(dataSource));
    }
}
