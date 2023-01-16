package nextstep.web;

import nextstep.repository.JdbcTemplateReservationRepository;
import nextstep.repository.ReservationRepository;
import nextstep.repository.ThemeRepository;
import nextstep.service.RoomEscapeService;
import nextstep.service.ThemeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class RoomEscapeApplication {
    public static void main(String[] args) {
        SpringApplication.run(RoomEscapeApplication.class);
    }

    @Bean
    public ReservationRepository reservationRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcTemplateReservationRepository(jdbcTemplate);
    }

    @Bean
    public RoomEscapeService roomEscapeService(ReservationRepository reservationRepository, ThemeRepository themeRepository) {
        return new RoomEscapeService(reservationRepository, themeRepository);
    }

    @Bean
    public ThemeRepository themeRepository(JdbcTemplate jdbcTemplate) {
        return new ThemeRepository(jdbcTemplate);
    }

    @Bean
    public ThemeService themeService(ThemeRepository themeRepository) {
        return new ThemeService(themeRepository);
    }
}
