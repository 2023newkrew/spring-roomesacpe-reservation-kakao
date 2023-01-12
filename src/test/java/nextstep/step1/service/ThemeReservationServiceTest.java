package nextstep.step1.service;

import nextstep.step1.RoomEscapeWebApplication;
import nextstep.step1.domain.Reservations;
import nextstep.step1.dto.ReservationDetail;
import nextstep.step1.dto.ReservationDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = RoomEscapeWebApplication.class)
class ThemeReservationServiceTest {
    public static final String RESERVATION_DATE = "2022-12-20";
    @Autowired
    private ThemeReservationService themeReservationService;

    @Test
    @DisplayName("방탈출 예약하기")
    void test1(){
        ReservationDto reservationDto = makeRandomReservationDto(RESERVATION_DATE, "13:01");

        Long reservationId = themeReservationService.reserve(reservationDto);
        ReservationDetail findReservation = themeReservationService.findById(reservationId);
        assertThat(findReservation).isNotNull();
    }

    @Test
    @DisplayName("이미 예약된 방탈출 예약을 취소한다.")
    void test2(){
        ReservationDto reservationDto = makeRandomReservationDto(RESERVATION_DATE, "13:02");
        Long reservationId = themeReservationService.reserve(reservationDto);

        System.out.println("reservationId = " + reservationId);
        themeReservationService.cancelById(reservationId);
        assertThat(themeReservationService.findById(reservationId)).isNull();
    }

    @Test
    @DisplayName("존재하지 않는 예약을 취소할 수 없다.")
    void test3(){
        assertThatThrownBy(() -> themeReservationService.cancelById(1000L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예약된 방을 조회한다.")
    void test4() {
        ReservationDto randomReservation = makeRandomReservationDto(RESERVATION_DATE, "13:04");
        Long reservationId = themeReservationService.reserve(randomReservation);
        System.out.println(Reservations.getInstance().findAll());
        System.out.println(reservationId);
        ReservationDetail reservationDetail = themeReservationService.findById(reservationId);

        assertThat(reservationDetail.getName()).isEqualTo(randomReservation.getName());
    }

    @Test
    @DisplayName("예약되지 않은 방을 조회한다.")
    void test5() {
        ReservationDetail findReservation = themeReservationService.findById(100L);

        assertThat(findReservation).isNull();
    }

    @Test
    @DisplayName("날짜와 시간이 같은 예약은 할 수 없다.")
    void test7() {
        ReservationDto reservation1 = makeRandomReservationDto(RESERVATION_DATE, "13:07");
        ReservationDto reservation2 = makeRandomReservationDto(RESERVATION_DATE, "13:07");

        themeReservationService.reserve(reservation1);
        Assertions.assertThatThrownBy(() -> themeReservationService.reserve(reservation2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    ReservationDto makeRandomReservationDto(String date, String time){
        List<String> names = List.of("omin", "ethan", "java");

        int index = ThreadLocalRandom.current().nextInt(3);

        return new ReservationDto(date, time, names.get(index), null);
    }

}
