package roomescape.config;

import nextstep.RoomEscapeConsoleApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import roomescape.repository.ReservationJdbcRepository;
import roomescape.repository.ThemeMockRepository;

@Configuration
public class ApplicationConfig {
    @Bean
    public RoomEscapeConsoleApplication consoleApplication(ReservationJdbcRepository reservationJdbcRepository, ThemeMockRepository themeRepository) {
        return new RoomEscapeConsoleApplication(reservationJdbcRepository, themeRepository);
    }
}
