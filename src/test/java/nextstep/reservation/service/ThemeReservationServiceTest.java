package nextstep.reservation.service;

import nextstep.RoomEscapeWebApplication;
import nextstep.exception.EntityNotFoundException;
import nextstep.reservation.ThemeReservationMock;
import nextstep.reservation.dto.ReservationDetail;
import nextstep.reservation.dto.ReservationDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = RoomEscapeWebApplication.class)
@Transactional
class ThemeReservationServiceTest {
    public static final String RESERVATION_DATE = "2022-12-20";
    @Autowired
    private ThemeReservationService themeReservationService;

    @Test
    @DisplayName("방탈출 예약하기")
    void test1(){
        ReservationDto reservationDto = ThemeReservationMock.makeRandomReservationDto();

        Long reservationId = themeReservationService.reserve(reservationDto);
        Optional<ReservationDetail> findReservation = themeReservationService.findById(reservationId);
        assertThat(findReservation).isPresent();
    }

    @Test
    @DisplayName("이미 예약된 방탈출 예약을 취소한다.")
    void test2(){
        ReservationDto reservationDto = ThemeReservationMock.makeRandomReservationDto();
        Long reservationId = themeReservationService.reserve(reservationDto);

        themeReservationService.cancelById(reservationId);
        Assertions.assertThatThrownBy(() -> themeReservationService.findById(reservationId))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("존재하지 않는 예약을 취소할 수 없다.")
    void test3(){
        assertThatThrownBy(() -> themeReservationService.cancelById(1000L))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("예약되지 않은 방을 조회한다.")
    void test5(){
        Assertions.assertThatThrownBy(() -> themeReservationService.findById(100L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("날짜와 시간이 같은 예약은 할 수 없다.")
    void test7() {
        ReservationDto reservation1 = ThemeReservationMock.makeRandomReservationDto(RESERVATION_DATE, "13:07");
        ReservationDto reservation2 = ThemeReservationMock.makeRandomReservationDto(RESERVATION_DATE, "13:07");

        themeReservationService.reserve(reservation1);
        Assertions.assertThatThrownBy(() -> themeReservationService.reserve(reservation2))
                .isInstanceOf(RuntimeException.class);
    }
}
