package nextstep.service.reservation;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.service.ReservationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReservationServiceTest {

    @Autowired
    ReservationService reservationService;
    static Theme theme;

    @BeforeAll
    static void setUpTheme() {
        theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
    }

    @Test
    void 예약을_생성할_수_있다() {
        assertDoesNotThrow(() -> reservationService.createReservation(
                LocalDate.parse("2022-01-04"), LocalTime.parse("13:00"), "bryan", theme));
    }

    @Test
    void 중복된_일시에_예약할_수_없다() {

        Reservation reservation = new Reservation(
                1L, LocalDate.parse("2022-01-02"), LocalTime.parse("13:00"), "bryan", theme);
        Reservation duplicatedReservation = new Reservation(
                1L, LocalDate.parse("2022-01-02"), LocalTime.parse("13:00"), "bryan", theme);

        reservationService.createReservation(
                LocalDate.parse("2022-01-04"), LocalTime.parse("13:00"), "bryan", theme);

        assertThatThrownBy(() -> reservationService.createReservation(
                LocalDate.parse("2022-01-04"), LocalTime.parse("13:00"), "bryan", theme))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("이미 예약된 일시에는 예약이 불가능합니다.");
    }

    @Test
    void 예약을_취소할_수_있다() {
        //given
        Reservation reservation = new Reservation(
                1L, LocalDate.parse("2022-01-04"), LocalTime.parse("13:00"), "bryan", theme);

        Long savedId = reservationService.createReservation(
                reservation.getDate(), reservation.getTime(), reservation.getName(), reservation.getTheme());

        //when
        reservationService.deleteReservation(savedId);

        //then
        assertThatThrownBy(() -> reservationService.findById(savedId))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void 예약을_조회할_수_있다() {
        //given
        Reservation reservation = new Reservation(
                1L, LocalDate.parse("2022-01-04"), LocalTime.parse("13:00"), "bryan", theme);

        Long savedId = reservationService.createReservation(
                reservation.getDate(), reservation.getTime(), reservation.getName(), reservation.getTheme());

        //when
        Reservation savedReservation = reservationService.findById(savedId);

        //then
        assertThat(reservation.getName()).isEqualTo(savedReservation.getName());
        assertThat(reservation.getDate()).isEqualTo(savedReservation.getDate());
        assertThat(reservation.getTheme().getName()).isEqualTo(savedReservation.getTheme().getName());
    }

}