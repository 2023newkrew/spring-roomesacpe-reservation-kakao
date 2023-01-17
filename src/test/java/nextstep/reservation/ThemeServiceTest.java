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

import java.time.LocalDate;
import java.time.LocalTime;

import static nextstep.reservation.exception.RoomEscapeExceptionCode.RESERVATION_EXIST;

@SpringBootTest
public class ThemeServiceTest {
    @Autowired
    ThemeService themeService;
    @Autowired
    ReservationService reservationService;


    @Test
    @DisplayName("테마가 예약에 등록돼있으면 삭제에 실패한다.")
    void Theme_deletion_fail_when_it_has_been_registered_in_any_Reservation() {
        //given
        ThemeRequest themeRequest = ThemeRequest.builder().name("호러").desc("매우무서운").price(25000).build();
        ThemeResponse themeResponse = themeService.registerTheme(themeRequest);

        ReservationRequest reservationRequest = ReservationRequest.builder().date(LocalDate.parse("2022-12-05")).time(LocalTime.parse("10:00")).name("herbi").themeId(themeResponse.getId()).build();
        reservationService.registerReservation(reservationRequest);

        //when
        //then
        Assertions.assertThatThrownBy(
                        () -> themeService.deleteById(themeResponse.getId()))
                .isInstanceOf(RoomEscapeException.class)
                .hasMessage(RESERVATION_EXIST.getMessage());

    }
}
