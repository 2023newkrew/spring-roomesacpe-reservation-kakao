package nextstep.web.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.domain.Theme;
import nextstep.web.VO.ReservationRequestVO;
import nextstep.web.exceptions.ErrorCode;
import nextstep.web.exceptions.ThemeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@DisplayName("Theme Service")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ThemeServiceTest {
    @Autowired
    private ThemeService themeService;
    @Autowired
    private ReservationService reservationService;

    private Theme theme;

    @BeforeEach
    void setUp() {
        theme = new Theme("테마이름", "테마설명", 22000);
    }

    @Test
    @DisplayName("동일한 이름을 가진 테마가 존재할 경우 예외 처리 테스트")
    public void themeAlreadyExistsTest() {
        themeService.createTheme(theme);
        assertThatThrownBy(() -> themeService.createTheme(theme))
                .isInstanceOf(ThemeException.class)
                .hasMessage(ErrorCode.ALREADY_THEME_EXISTS.getMessage());
    }

    @Test
    @DisplayName("테마 삭제 시 테마가 없을 경우 예외 처리 테스트")
    public void noViewedThemeTest() {
        assertThatThrownBy(() -> themeService.deleteTheme(1L))
                .isInstanceOf(ThemeException.class)
                .hasMessage(ErrorCode.THEME_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("테마 삭제 시 해당 테마를 가진 예약이 있을 경우 예외 처리 테스트")
    public void ThemeWithExistReservationTest() {
        themeService.createTheme(new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000));
        ReservationRequestVO reservationRequestVO = new ReservationRequestVO(
                LocalDate.of(2022, 8, 11),
                LocalTime.of(13, 0),
                "name",
                "테마이름"
        );
        reservationService.createReservation(reservationRequestVO);
        assertThatThrownBy(() -> themeService.deleteTheme(1L))
                .isInstanceOf(ThemeException.class)
                .hasMessage(ErrorCode.RESERVATION_WITH_THIS_THEME_EXISTS.getMessage());
    }
}
