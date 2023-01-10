package service;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.repository.ReservationMemoryRepository;
import nextstep.service.ReservationService;
import nextstep.service.ReservationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

class ReservationServiceImplTest {

    ReservationService reservationService;
    Theme testTheme;

    @BeforeEach
    void setUp() {
        testTheme = new Theme("검은방", "밀실 탈출", 30_000);
        reservationService = new ReservationServiceImpl(new ReservationMemoryRepository());
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
                null, "2023-01-01", "13:00", "john", null);
        Reservation reservation2 = generateReservation(
                null, "2023-01-01", "13:00", "kim", null);

        assertThatNoException()
                .isThrownBy(() -> reservationService.reserve(reservation1));
        assertThatThrownBy(() -> reservationService.reserve(reservation2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("예약 시 날짜와 시간이 동일한 예약이 없는 경우 예약 성공")
    @Test
    void reserve_success() {
        Reservation reservation1 = generateReservation(
                null, "2023-01-01", "13:00", "john", null);
        Reservation reservation2 = generateReservation(
                null, "2023-01-01", "14:00", "john", null);
        Reservation reservation3 = generateReservation(
                null, "2023-01-02", "13:00", "john", null);

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
                null, "2023-01-01", "13:00", "john", null);
        reservationService.reserve(reservation);
        Long id = 1L;

        Reservation expected = generateReservation(
                id, "2023-01-01", "13:00", "john", testTheme);

        //when
        Reservation result = reservationService.findReservation(id);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("예약 조회에 실패하면 예외 발생")
    @Test
    void findReservation_fail() {
        assertThatThrownBy(() -> reservationService.findReservation(1L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("예약 취소에 성공하면 true 반환")
    @Test
    void delete_success() {
        Reservation reservation = generateReservation(
                null, "2023-01-01", "13:00", "john", testTheme);
        reservationService.reserve(reservation);
        Long id = 1L;

        //when
        boolean result = reservationService.cancelReservation(id);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("예약 취소에 실패하면 false 반환")
    @Test
    void delete_fail() {
        //then
        assertThat(reservationService.cancelReservation(1L)).isFalse();
    }
}
