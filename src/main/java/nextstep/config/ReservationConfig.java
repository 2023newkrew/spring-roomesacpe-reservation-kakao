package nextstep.config;

import nextstep.repository.ReservationRepository;
import nextstep.repository.ReservationRepositoryImpl;
import nextstep.service.ReservationService;
import nextstep.service.ReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class ReservationConfig {

    final JdbcTemplate jdbcTemplate;

    public ReservationConfig(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Bean
    ReservationRepository reservationRepository() {
        return new ReservationRepositoryImpl(jdbcTemplate);
    }

    @Bean
    ReservationService reservationService() {
        return new ReservationServiceImpl(reservationRepository());
    }
}
