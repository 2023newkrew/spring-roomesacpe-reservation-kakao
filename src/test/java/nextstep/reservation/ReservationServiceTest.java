package nextstep.reservation;

import nextstep.reservation.dto.ReservationRequest;
import nextstep.reservation.entity.Theme;
import nextstep.reservation.exception.RoomEscapeException;
import nextstep.reservation.repository.ThemeRepository;
import nextstep.reservation.service.ReservationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
    private ThemeRepository themeRepository;
    private ReservationRequest reservation;

    @BeforeEach
    void setUp() {
        Theme theme = new Theme(null, "워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
        Theme savedTheme = themeRepository.save(theme);

        this.reservation = new ReservationRequest(LocalDate.parse("2022-08-12"), LocalTime.parse("13:00"), "name", savedTheme.getId());
    }

    @Test
    @DisplayName("이미 예약이 존재하는 시간에 예약 생성 시도할 때 예외 발생")
    void duplicate_time_Reservation_Exception() {
        //given
        ReservationRequest reservationDuplicated = new ReservationRequest(LocalDate.parse("2022-08-12"), LocalTime.parse("13:00"), "name2", reservation.getThemeId());
        reservationService.registerReservation(reservationDuplicated);

        //when
        //then
        Assertions.assertThatThrownBy(
                () -> reservationService.registerReservation(reservationDuplicated)
        ).isInstanceOf(RoomEscapeException.class).hasMessage(DUPLICATE_TIME_RESERVATION.getMessage());
    }

    @Test
    @DisplayName("생성되지 않은 예약의 ID를 입력할 때 예약 발생")
    void find_have_never_registered_Reservation_by_id_Exception() {
        //given
        //when
        //then
        Assertions.assertThatThrownBy(
                () -> reservationService.findById(1L)
        ).isInstanceOf(RoomEscapeException.class).hasMessage(NO_SUCH_RESERVATION.getMessage());
    }
}