package nextstep.reservation.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.reservation.dto.request.ReservationRequestDto;
import nextstep.reservation.entity.Theme;
import nextstep.reservation.exceptions.exception.DuplicateReservationTimeException;
import nextstep.reservation.exceptions.exception.DuplicateReservationNameException;
import nextstep.reservation.repository.reservation.ReservationMemoryRepository;

import nextstep.reservation.repository.theme.ThemeMemoryRepository;
import nextstep.reservation.repository.theme.ThemeRepository;
import org.junit.jupiter.api.Test;

class ReservationServiceTest {

    private final ReservationService reservationService;


    private final ReservationRequestDto reservationRequestDto = ReservationRequestDto.builder()
            .date(LocalDate.of(1982, 2, 19))
            .time(LocalTime.of(2, 2))
            .name("name")
            .themeId(1L)
            .build();

    public ReservationServiceTest() {
        ThemeRepository themeRepository = new ThemeMemoryRepository();
        themeRepository.add(
                Theme.builder()
                .id(1L)
                .name("워너고홈")
                .desc("병맛 어드벤처 회사 코믹물")
                .price(29_000)
                .build());
        reservationService = new ReservationService(
                new ReservationMemoryRepository(),
                themeRepository
        );
    }

    @Test
    void 예약이_차있는_시간에_예약을_하게_되면_예외가_발생한다() {
        reservationService.addReservation(reservationRequestDto);
        assertThatThrownBy(() -> reservationService.addReservation(reservationRequestDto))
                .isInstanceOf(DuplicateReservationTimeException.class);
    }

    @Test
    void 이미_존재하는_이름의_예약을_하게_되면_예외가_발생한다() {
        reservationService.addReservation(reservationRequestDto);
        ReservationRequestDto sameNameReservationRequestDto = ReservationRequestDto.builder()
                .date(LocalDate.of(1982, 2, 20))
                .time(LocalTime.of(2, 2))
                .name("name")
                .themeId(1L)
                .build();
        assertThatThrownBy(() -> reservationService.addReservation(sameNameReservationRequestDto))
                .isInstanceOf(DuplicateReservationNameException.class);

    }

}