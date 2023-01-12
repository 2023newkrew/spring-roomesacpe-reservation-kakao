package nextstep.config;

import nextstep.presentation.argumentresolver.PaginationArgumentResolver;
import nextstep.utils.ThemeRequestValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public ThemeRequestValidator themeRequestValidator() {
        return new ThemeRequestValidator();
    }

    @Bean
    public PaginationArgumentResolver paginationArgumentResolver() {
        return new PaginationArgumentResolver(themeRequestValidator());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(paginationArgumentResolver());
    }
}
