package nextstep.web;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.exception.ReservationDuplicateException;
import nextstep.exception.ReservationNotFoundException;
import nextstep.repository.JdbcTemplateReservationRepository;
import nextstep.repository.ReservationRepository;
import nextstep.service.RoomEscapeService;
import nextstep.web.dto.ReservationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class RoomEscapeServiceTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RoomEscapeService roomEscapeService;

    @BeforeEach
    void setUp() {
        ReservationRepository repository = new JdbcTemplateReservationRepository(jdbcTemplate);
        roomEscapeService = new RoomEscapeService(repository);
    }

    @DisplayName("존재하지 않는 예약을 조회할 경우 예외가 발생한다")
    @Test
    void getNotFoundReservation() {
        assertThatThrownBy(() -> roomEscapeService.getReservation(1L))
                .isInstanceOf(ReservationNotFoundException.class);
    }

    @DisplayName("예약 생성시 같은 날짜와 시간의 예약이 존재할 경우 예외가 발생한다")
    @Test
    void createDuplicateReservation() {
        String name = "겹치는 예약";
        LocalDate date = LocalDate.of(2022, 11, 11);
        LocalTime time = LocalTime.of(19, 00);
        ReservationRequest request = new ReservationRequest(name, date, time);
        roomEscapeService.createReservation(request);

        assertThatThrownBy(() -> roomEscapeService.createReservation(request))
                .isInstanceOf(ReservationDuplicateException.class);
    }
}
