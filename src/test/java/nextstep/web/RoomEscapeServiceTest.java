package nextstep.web;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.exception.ReservationDuplicateException;
import nextstep.exception.ReservationNotFoundException;
import nextstep.model.Theme;
import nextstep.repository.JdbcTemplateReservationRepository;
import nextstep.repository.ReservationRepository;
import nextstep.repository.ThemeRepository;
import nextstep.service.RoomEscapeService;
import nextstep.service.ThemeService;
import nextstep.web.dto.ReservationRequest;
import nextstep.web.dto.ThemeRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class RoomEscapeServiceTest {

    @Autowired
    private RoomEscapeService roomEscapeService;

    @Autowired
    private ThemeService themeService;

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @AfterEach
    void tearDown() {
        reservationRepository.deleteAll();
        themeRepository.deleteAll();
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
        Theme savedTheme = themeService.save(new ThemeRequest("베루스홈", "베루스의 집", 50_000));

        String name = "겹치는 예약";
        LocalDate date = LocalDate.of(2022, 11, 11);
        LocalTime time = LocalTime.of(19, 00);
        ReservationRequest request = new ReservationRequest(name, date, time, savedTheme.getId());
        roomEscapeService.createReservation(request);

        assertThatThrownBy(() -> roomEscapeService.createReservation(request))
                .isInstanceOf(ReservationDuplicateException.class);
    }
}
