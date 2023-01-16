package nextstep.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.exception.ReservationDuplicateException;
import nextstep.exception.ReservationNotFoundException;
import nextstep.exception.ThemeNotFoundException;
import nextstep.model.Reservation;
import nextstep.model.Theme;
import nextstep.repository.JdbcTemplateReservationRepository;
import nextstep.repository.ReservationRepository;
import nextstep.repository.ThemeRepository;
import nextstep.service.RoomEscapeService;
import nextstep.service.ThemeService;
import nextstep.web.dto.ReservationRequest;
import nextstep.web.dto.ThemeRequest;
import org.assertj.core.api.Assertions;
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
        Theme theme = themeService.save(new ThemeRequest("베루스홈", "베루스의 집", 50_000));

        String name = "겹치는 예약";
        LocalDate date = LocalDate.of(2022, 11, 11);
        LocalTime time = LocalTime.of(19, 00);
        ReservationRequest request = new ReservationRequest(name, date, time, theme.getId());
        roomEscapeService.createReservation(request);

        assertThatThrownBy(() -> roomEscapeService.createReservation(request))
                .isInstanceOf(ReservationDuplicateException.class);
    }

    @DisplayName("테마가 다른 같은 시간의 예약은 생성할 수 있다.")
    @Test
    void createDuplicatedTimeReservationToDifferTheme() {
        Theme theme1 = themeService.save(new ThemeRequest("베루스홈", "베루스의 집", 50_000));
        Theme theme2 = themeService.save(new ThemeRequest("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000));

        LocalDate date = LocalDate.of(2022, 11, 11);
        LocalTime time = LocalTime.of(19, 00);
        ReservationRequest request1 = new ReservationRequest("예약", date, time, theme1.getId());
        ReservationRequest request2 = new ReservationRequest("예약", date, time, theme2.getId());

        Reservation reservation1 = roomEscapeService.createReservation(request1);
        Reservation reservation2 = roomEscapeService.createReservation(request2);

        assertThat(reservationRepository.findById(reservation1.getId())).isNotEmpty();
        assertThat(reservationRepository.findById(reservation2.getId())).isNotEmpty();
    }

    @DisplayName("존재하지 않는 테마로 예약을 생성할 수 없다.")
    @Test
    void createReservationByNotExistTheme() {
        String name = "예약";
        LocalDate date = LocalDate.of(2022, 11, 11);
        LocalTime time = LocalTime.of(19, 00);
        Long notFoundThemeId = 1L;
        ReservationRequest request = new ReservationRequest(name, date, time, notFoundThemeId);

        assertThatThrownBy(() -> roomEscapeService.createReservation(request))
                .isInstanceOf(ThemeNotFoundException.class);
    }
}
