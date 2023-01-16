package nextstep.reservation;

import nextstep.reservation.dto.ReservationRequest;
import nextstep.reservation.dto.ThemeRequest;
import nextstep.reservation.dto.ThemeResponse;
import nextstep.reservation.exception.RoomEscapeException;
import nextstep.reservation.service.ReservationService;
import nextstep.reservation.service.ThemeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static nextstep.reservation.exception.RoomEscapeExceptionCode.DUPLICATE_TIME_RESERVATION;
import static nextstep.reservation.exception.RoomEscapeExceptionCode.NO_SUCH_RESERVATION;

@SpringBootTest
@Transactional
class ReservationServiceTest {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ThemeService themeService;

    @Test
    @DisplayName("이미 예약이 존재하는 시간에 예약 생성 시도할 때 예외 발생한다.")
    void duplicate_time_Reservation_Exception() {
        //given
        ThemeRequest themeRequest = new ThemeRequest("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
        ThemeResponse themeResponse = themeService.registerTheme(themeRequest);

        ReservationRequest reservation = new ReservationRequest(LocalDate.parse("2022-08-12"), LocalTime.parse("13:00"), "name", themeResponse.getId());
        reservationService.registerReservation(reservation);

        ReservationRequest reservationDuplicated = new ReservationRequest(LocalDate.parse("2022-08-12"), LocalTime.parse("13:00"), "name2", reservation.getThemeId());

        //when
        //then
        Assertions.assertThatThrownBy(
                () -> reservationService.registerReservation(reservationDuplicated)
        ).isInstanceOf(RoomEscapeException.class).hasMessage(DUPLICATE_TIME_RESERVATION.getMessage());
    }

    @Test
    @DisplayName("생성되지 않은 예약의 ID로 예약 조회할 때 에외 발생한다.")
    void find_have_never_registered_Reservation_by_id_Exception() {
        //given
        //when
        //then
        Assertions.assertThatThrownBy(
                () -> reservationService.findById(1L)
        ).isInstanceOf(RoomEscapeException.class).hasMessage(NO_SUCH_RESERVATION.getMessage());
    }
}