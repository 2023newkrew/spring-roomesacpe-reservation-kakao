package nextstep.service;

import nextstep.domain.dto.reservation.CreateReservationDto;
import nextstep.domain.reservation.Reservation;
import nextstep.domain.theme.Theme;
import nextstep.exception.DuplicateTimeReservationException;
import nextstep.exception.IllegalReservationTimeException;
import nextstep.exception.NoReservationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest
@DisplayName("Reservation Service 테스트")
@Sql(scripts = {"classpath:recreate.sql"})
public class ReservationServiceTest {

    private final ReservationService reservationService;
    private final Theme defaultTheme = new Theme(1l,"워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);


    @Autowired
    public ReservationServiceTest(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @DisplayName("예약 시간이 다르면 예외를 발생하지 않고 예약에 성공")
    @Test
    void reservationSuccess() {
        CreateReservationDto createReservationDto1 = new CreateReservationDto("2022-08-11", "13:30:00", "name", 1l);
        CreateReservationDto createReservationDto2 = new CreateReservationDto("2022-08-11", "14:00:00", "name2", 1l);

        reservationService.addReservation(createReservationDto1);

        assertThatNoException()
                .isThrownBy(()->reservationService.addReservation(createReservationDto2));
    }

    @DisplayName("중복된 시간에 예약을 추가시 예외 발생")
    @Test
    void duplicateReservationTest() {
        CreateReservationDto createReservationDto1 = new CreateReservationDto("2022-08-11", "14:30:00", "name", 1l);
        CreateReservationDto createReservationDto2 = new CreateReservationDto("2022-08-11", "14:30:00", "name2", 1l);

        reservationService.addReservation(createReservationDto1);

        assertThatThrownBy(()->reservationService.addReservation(createReservationDto2))
                .isInstanceOf(DuplicateTimeReservationException.class);
    }

    @DisplayName("30분 단위가 아닌 예약을 추가시 예외 발생")
    @Test
    void illegalReservationTest() {
        CreateReservationDto createReservationDto = new CreateReservationDto("2022-08-11", "14:35:00", "name", 1l);

        assertThatThrownBy(()->reservationService.addReservation(createReservationDto))
                .isInstanceOf(IllegalReservationTimeException.class);
    }

    @DisplayName("추가된 예약을 조회시 동일한 값이 들어있는지 확인")
    @Test
    void getReservationTest() {
        // given
        CreateReservationDto createReservationDto = new CreateReservationDto("2022-08-11", "14:30:00", "name", 1l);

        // when
        long id = reservationService.addReservation(createReservationDto);
        Reservation realReservation = reservationService.getReservation(id);
        Reservation expectedReservation = new Reservation(id, LocalDate.of(2022,8,11), LocalTime.of(14,30), "name", defaultTheme);

        // then
        assertThat(realReservation).isEqualTo(expectedReservation);
    }

    @DisplayName("존재하지 않는 예약을 조회하면 예외 발생")
    @Test
    void noReservationTest() {
        assertThatThrownBy(()-> reservationService.getReservation(1l))
                .isInstanceOf(NoReservationException.class);
    }
}
