package nextstep.web;

import nextstep.repository.JdbcTemplateReservationRepository;
import nextstep.repository.ReservationRepository;
import nextstep.service.RoomEscapeService;
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
    public RoomEscapeService roomEscapeService(ReservationRepository reservationRepository) {
        return new RoomEscapeService(reservationRepository);
    }
}
