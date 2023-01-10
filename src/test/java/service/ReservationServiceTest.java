package service;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.repository.ReservationMemoryRepository;
import nextstep.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

class ReservationServiceTest {

    ReservationService reservationService;
    Theme testTheme;

    @BeforeEach
    void setUp() {
        testTheme = new Theme("Theme", "Theme desc", 10_000);
        reservationService = new ReservationService(new ReservationMemoryRepository());
    }

    private Reservation generateReservation(Long id, String date, String time, String name, Theme theme) {
        return new Reservation(
                id,
                LocalDate.parse(date),
                LocalTime.parse(time),
                name,
                theme
        );
    }

    @DisplayName("예약 시 날짜와 시간이 모두 동일한 예약이 이미 존재하는 경우 예외 발생")
    @Test
    void reserve_duplicate() {
        Reservation reservation1 = generateReservation(
                null, "2023-01-01", "13:00", "john", testTheme);
        Reservation reservation2 = generateReservation(
                null, "2023-01-01", "13:00", "kim", testTheme);

        assertThatNoException()
                .isThrownBy(() -> reservationService.reserve(reservation1));
        assertThatThrownBy(() -> reservationService.reserve(reservation2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("예약 시 날짜와 시간이 동일한 예약이 없는 경우 예약 성공")
    @Test
    void reserve_success() {
        Reservation reservation1 = generateReservation(
                null, "2023-01-01", "13:00", "john", testTheme);
        Reservation reservation2 = generateReservation(
                null, "2023-01-01", "14:00", "john", testTheme);
        Reservation reservation3 = generateReservation(
                null, "2023-01-02", "13:00", "john", testTheme);

        assertThatNoException()
                .isThrownBy(() -> reservationService.reserve(reservation1));
        assertThatNoException()
                .isThrownBy(() -> reservationService.reserve(reservation2));
        assertThatNoException()
                .isThrownBy(() -> reservationService.reserve(reservation3));
    }

    @DisplayName("예약 조회에 성공하면 Reservation 객체 반환")
    @Test
    void findReservation_success() {
        //given
        Reservation reservation = generateReservation(
                null, "2023-01-01", "13:00", "john", testTheme);
        reservationService.reserve(reservation);
        Long id = 1L;

        //when
        Reservation result = reservationService.findReservation(id);

        //then
        assertThat(result).isEqualTo(reservation);
    }


    @DisplayName("예약 조회에 실패하면 예외 발생")
    @Test
    void findReservation_fail() {
        assertThatThrownBy(() -> reservationService.findReservation(1L))
                .isInstanceOf(NoSuchElementException.class);
    }
}
