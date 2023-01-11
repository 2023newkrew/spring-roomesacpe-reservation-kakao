package roomescape.config;

import nextstep.RoomEscapeConsoleApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import roomescape.repository.ReservationJdbcRepository;

@Configuration
public class ApplicationConfig {
    @Bean
    public RoomEscapeConsoleApplication consoleApplication(ReservationJdbcRepository reservationJdbcRepository) {
        return new RoomEscapeConsoleApplication(reservationJdbcRepository);
    }
}
