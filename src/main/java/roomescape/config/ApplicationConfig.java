package roomescape.config;

import nextstep.RoomEscapeConsoleApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import roomescape.repository.ReservationJdbcRepository;
import roomescape.repository.ThemeJdbcRepository;
import roomescape.service.ReservationService;
import roomescape.service.ThemeService;

@Configuration
public class ApplicationConfig {
    @Bean
    public RoomEscapeConsoleApplication consoleApplication(
            ReservationService reservationService,
            ThemeService themeService,
            ReservationJdbcRepository reservationRepository,
            ThemeJdbcRepository themeRepository
    ) {
        return new RoomEscapeConsoleApplication(reservationService, reservationRepository, themeService, themeRepository);
    }
}
