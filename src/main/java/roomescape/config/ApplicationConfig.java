package roomescape.config;

import nextstep.RoomEscapeConsoleApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import roomescape.repository.ReservationJdbcRepository;
import roomescape.repository.ThemeJdbcRepository;

@Configuration
public class ApplicationConfig {
    @Bean
    public RoomEscapeConsoleApplication consoleApplication(ReservationJdbcRepository reservationJdbcRepository, ThemeJdbcRepository themeRepository) {
        return new RoomEscapeConsoleApplication(reservationJdbcRepository, themeRepository);
    }
}
