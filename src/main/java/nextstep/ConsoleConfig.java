package nextstep;

import kakao.repository.reservation.ReservationJdbcRepository;
import kakao.repository.reservation.ReservationRepository;
import kakao.repository.theme.ThemeJdbcTemplateRepository;
import kakao.repository.theme.ThemeRepository;
import kakao.service.ReservationService;
import kakao.service.ThemeUtilService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@PropertySource("classpath:application.properties")
@EnableAutoConfiguration
public class ConsoleConfig {

    @Bean
    public ReservationRepository reservationRepository() {
        return new ReservationJdbcRepository();
    }

    @Bean
    public ThemeRepository themeRepository(JdbcTemplate jdbcTemplate) {
        return new ThemeJdbcTemplateRepository(jdbcTemplate);
    }

    @Bean
    public ThemeUtilService themeUtilService(ThemeRepository themeRepository) {
        return new ThemeUtilService(themeRepository);
    }

    @Bean
    public ReservationService reservationService(ReservationRepository reservationRepository,
                                                 ThemeUtilService themeUtilService) {
        return new ReservationService(reservationRepository, themeUtilService);
    }
}
