package nextstep.web;

import nextstep.exception.ReservationDuplicateException;
import nextstep.exception.ReservationNotFoundException;
import nextstep.service.ReservationService;
import nextstep.dto.ReservationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
class ReservationServiceTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private JdbcTemplateReservationRepository reservationRepository;

    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationRepository = new JdbcTemplateReservationRepository(jdbcTemplate);
        reservationService = new ReservationService(reservationRepository);
    }

    @DisplayName("존재하지 않는 예약을 조회할 경우 예외가 발생한다")
    @Test
    void getNotFoundReservation() {
        assertThatThrownBy(() -> reservationService.getReservation(1L))
                .isInstanceOf(ReservationNotFoundException.class);
    }

    @DisplayName("예약 생성시 같은 날짜와 시간의 예약이 존재할 경우 예외가 발생한다")
    @Test
    void createDuplicateReservation() {
        String name = "겹치는 예약";
        LocalDate date = LocalDate.of(2022, 11, 11);
        LocalTime time = LocalTime.of(19, 00);
        Long themeId = 1L;
        ReservationRequest request = new ReservationRequest(name, date, time, themeId);
        reservationService.createReservation(request);

        assertThatThrownBy(() -> reservationService.createReservation(request))
                .isInstanceOf(ReservationDuplicateException.class);
    }
}
