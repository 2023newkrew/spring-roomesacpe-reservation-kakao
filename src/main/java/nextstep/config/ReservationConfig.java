package nextstep.config;

import javax.sql.DataSource;
import nextstep.repository.ReservationRepository;
import nextstep.repository.ReservationRepositoryImpl;
import nextstep.service.ReservationService;
import nextstep.service.ReservationServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReservationConfig {
    @Bean
    ReservationRepository reservationRepository(DataSource dataSource) {
        return new ReservationRepositoryImpl(dataSource);
    }

    @Bean
    ReservationService reservationService(DataSource dataSource) {
        return new ReservationServiceImpl(reservationRepository(dataSource));
    }
}
