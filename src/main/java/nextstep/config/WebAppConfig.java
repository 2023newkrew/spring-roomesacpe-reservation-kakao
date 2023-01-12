package nextstep.config;

import javax.sql.DataSource;
import nextstep.repository.ReservationRepository;
import nextstep.repository.ReservationRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebAppConfig {
    @Bean
    ReservationRepository reservationRepository(DataSource dataSource) {
        return new ReservationRepositoryImpl(dataSource);
    }

}
