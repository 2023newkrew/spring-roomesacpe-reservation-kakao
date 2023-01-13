package nextstep.web.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.domain.Theme;
import nextstep.web.VO.ReservationRequestVO;
import nextstep.web.exceptions.ErrorCode;
import nextstep.web.exceptions.ReservationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("Reservation Service")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationServiceTest {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ThemeService themeService;

    @Order(1)
    @Test
    @DisplayName("동일한 날짜, 시간에 예약이 존재할 경우 예외 처리 테스트")
    public void reservationAlreadyExistsTest() {
        themeService.createTheme(new Theme("테마이름", "테마설명", 22000));
        ReservationRequestVO reservationRequestVO = new ReservationRequestVO(
                LocalDate.of(2022, 8, 11),
                LocalTime.of(13, 0),
                "name",
                "테마이름"
        );
        reservationService.createReservation(reservationRequestVO);
        assertThatThrownBy(() -> reservationService.createReservation(reservationRequestVO))
                .isInstanceOf(ReservationException.class)
                .hasMessage(ErrorCode.ALREADY_RESERVATION_EXISTS.getMessage());
    }

    @Order(2)
    @Test
    @DisplayName("테마 이름에 해당하는 테마가 없는 경우 예외 처리 테스트")
    public void noThemeTest() {
        ReservationRequestVO reservationRequestVO = new ReservationRequestVO(
                LocalDate.of(2022, 8, 11),
                LocalTime.of(13, 0),
                "name",
                "테마이름1"
        );
        reservationService.deleteReservation(1L);
        assertThatThrownBy(() -> reservationService.createReservation(reservationRequestVO))
                .isInstanceOf(ReservationException.class)
                .hasMessage(ErrorCode.THEME_NOT_FOUND.getMessage());
    }

    @Order(3)
    @Test
    @DisplayName("조회한 예약이 없을 경우 예외 처리 테스트")
    public void noViewedReservationTest() {
        assertThatThrownBy(() -> reservationService.lookupReservation(100L))
                .isInstanceOf(ReservationException.class)
                .hasMessage(ErrorCode.RESERVATION_NOT_FOUND.getMessage());
    }
}
