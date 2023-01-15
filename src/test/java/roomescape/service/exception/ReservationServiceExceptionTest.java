package roomescape.service.exception;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import roomescape.dao.reservation.ReservationDAO;
import roomescape.dao.theme.ThemeDAO;
import roomescape.dto.Reservation;
import roomescape.exception.BadRequestException;
import roomescape.service.reservation.ReservationService;
import roomescape.service.reservation.ReservationServiceInterface;

@DisplayName("예약 서비스 예외 테스트")
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ReservationServiceExceptionTest {

    private static final Long ID_DATA = 1L;
    private static final LocalDate DATE_DATA = LocalDate.parse("2022-08-01");
    private static final LocalTime TIME_DATA = LocalTime.parse("13:00");
    private static final String NAME_DATA = "test";
    private static final Long THEME_ID_DATA = 1L;

    private ReservationServiceInterface reservationService;

    @Mock
    private ReservationDAO reservationDAO;

    @Mock
    private ThemeDAO themeDAO;

    @BeforeEach
    void setUp() {
        reservationService = new ReservationService(reservationDAO, themeDAO);
    }

    @DisplayName("예약 생성) 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다.")
    @Test
    void failToCreateReservationSameDateAndSameTime() {
        Reservation reservation = new Reservation(DATE_DATA, TIME_DATA, NAME_DATA, THEME_ID_DATA);

        when(reservationDAO.exist(reservation)).thenReturn(true);

        assertThatThrownBy(() -> reservationService.create(reservation))
                .isInstanceOf(BadRequestException.class);
    }

    @DisplayName("예약 생성) 값이 포함되지 않았을 경우 예약 생설 불가")
    @ParameterizedTest
    @MethodSource("getFailToCreateReservationInvalidValueDate")
    void failToCreateReservationInvalidValue(Reservation reservation) {
        assertThatThrownBy(() -> reservationService.create(reservation))
                .isInstanceOf(BadRequestException.class);
    }

    private static Stream<Arguments> getFailToCreateReservationInvalidValueDate() {
        return Stream.of(
                Arguments.arguments(new Reservation(null, TIME_DATA, NAME_DATA, THEME_ID_DATA)),
                Arguments.arguments(new Reservation(DATE_DATA, null, NAME_DATA, THEME_ID_DATA)),
                Arguments.arguments(new Reservation(DATE_DATA, TIME_DATA, null, THEME_ID_DATA)),
                Arguments.arguments(new Reservation(DATE_DATA, TIME_DATA, NAME_DATA, null))
        );
    }

    @DisplayName("예약 생성) 존재하지 않는 테마를 지정할 경우 생성 불가")
    @Test
    void failToCreateReservationInvalidThemeId() {
        Reservation reservation = new Reservation(DATE_DATA, TIME_DATA, NAME_DATA, THEME_ID_DATA);

        when(reservationDAO.exist(reservation)).thenReturn(false);
        when(themeDAO.existId(THEME_ID_DATA)).thenReturn(false);

        assertThatThrownBy(() -> reservationService.create(reservation))
                .isInstanceOf(BadRequestException.class);
    }

    @DisplayName("예약 조회) ID가 없는 경우 조회 불가")
    @Test
    void failToFindReservationNotExist() {
        when(reservationDAO.find(ID_DATA)).thenReturn(null);

        assertThatThrownBy(() -> reservationService.find(ID_DATA))
                .isInstanceOf(BadRequestException.class);
    }

    @DisplayName("예약 삭제) ID가 없는 경우 삭제 불가")
    @Test
    void removeReservation() {
        when(reservationDAO.existId(ID_DATA)).thenReturn(false);

        assertThatThrownBy(() -> reservationService.remove(ID_DATA))
                .isInstanceOf(BadRequestException.class);
    }
}
