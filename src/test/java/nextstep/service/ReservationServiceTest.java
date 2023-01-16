package nextstep.service;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.exception.ReservationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static nextstep.exception.ErrorCode.DUPLICATED_RESERVATION_EXISTS;
import static nextstep.exception.ErrorCode.RESERVATION_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class ReservationServiceTest {

    @Autowired
    ReservationService reservationService;

    Theme theme = new Theme(4L, "테스트 테마", "테스트용 테마임", 1234);

    Reservation reservation = new Reservation(
            1L, LocalDate.parse("2022-01-02"), LocalTime.parse("13:00"), "bryan", theme.getId());

    @Test
    void 예약을_생성할_수_있다() {
        assertDoesNotThrow(() -> reservationService.createReservation(reservation));
    }

    @Test
    void 중복된_일시에_예약할_수_없다() {
        ReservationException e = assertThrows(ReservationException.class, () -> {
            reservationService.createReservation(reservation);
            reservationService.createReservation(reservation);
        });
        assertThat(e.getErrorCode()).isEqualTo(DUPLICATED_RESERVATION_EXISTS);
    }

    @Test
    void 예약을_조회할_수_있다() {
        //given
        Long savedId = reservationService.createReservation(reservation);

        //when
        Reservation savedReservation = reservationService.findById(savedId);

        //then
        assertThat(reservation.getName()).isEqualTo(savedReservation.getName());
        assertThat(reservation.getDate()).isEqualTo(savedReservation.getDate());
        assertThat(reservation.getThemeId()).isEqualTo(savedReservation.getThemeId());
    }

    @Test
    void 없는_예약을_조회할_수_없다() {
        //given
        Long fakeId = 1L;

        //when, then
        ReservationException e = assertThrows(ReservationException.class,
                () -> reservationService.findById(fakeId));
        assertThat(e.getErrorCode()).isEqualTo(RESERVATION_NOT_FOUND);
    }

    @Test
    void 예약을_취소할_수_있다() {
        //given
        Long savedId = reservationService.createReservation(reservation);

        //when, then
        assertDoesNotThrow(() -> reservationService.deleteReservation(savedId));
    }

    @Test
    void 없는_예약을_취소할_수_없다() {
        //given
        Long fakeId = 1L;

        //when, then
        ReservationException e = assertThrows(ReservationException.class,
                () -> reservationService.deleteReservation(fakeId));
        assertThat(e.getErrorCode()).isEqualTo(RESERVATION_NOT_FOUND);
    }

}