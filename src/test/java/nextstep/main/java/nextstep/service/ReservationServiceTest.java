package nextstep.main.java.nextstep.service;

import nextstep.main.java.nextstep.domain.Reservation;
import nextstep.main.java.nextstep.domain.ReservationCreateRequestDto;
import nextstep.main.java.nextstep.domain.Theme;
import nextstep.main.java.nextstep.exception.exception.DuplicateReservationException;
import nextstep.main.java.nextstep.exception.exception.NoSuchReservationException;
import nextstep.main.java.nextstep.repository.ReservationRepository;
import nextstep.main.java.nextstep.repository.ThemeRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private ThemeRepository themeRepository;
    @InjectMocks
    private ReservationService reservationService;

    @Test
    @DisplayName("예약 단건 조회 테스트")
    void findOneByIdTest() {
        Reservation savedReservation = new Reservation(1L, LocalDate.of(2023, 1, 9), LocalTime.of(1, 30), "name", 1L);
        when(reservationRepository.findOne(savedReservation.getId())).thenReturn(Optional.of(savedReservation));
        assertThat(reservationService.findOneById(1L)).isEqualTo(savedReservation);
    }


    @Test
    @DisplayName("조회 실패 시 예외 발생 테스트")
    void findOneByIdNoSuchReservationExceptionTest() {
        when(reservationRepository.findOne(any(Long.class))).thenThrow(NoSuchReservationException.class);
        assertThatCode(() -> reservationService.findOneById(any(Long.class))).isInstanceOf(NoSuchReservationException.class);
    }

    @Test
    @DisplayName("예약 단건 삭제 테스트")
    void deleteOneByIdTest() {
        when(reservationRepository.deleteOne(any(Long.class))).thenReturn(true);
        assertThatCode(() -> reservationService.deleteOneById(any(Long.class))).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("존재하지 않는 예약 삭제시 에외 발생 테스트")
    void deleteOneByIdNoSuchReservationExceptionTest() {
        when(reservationRepository.deleteOne(any(Long.class))).thenReturn(false);
        assertThatCode(() -> reservationService.deleteOneById(any(Long.class))).isInstanceOf(NoSuchReservationException.class);
    }
    @Disabled
    @Test
    @DisplayName("이름 중복 예약 생성 시 예외 발생 테스트")
    void createDuplicateTest() {
        ReservationCreateRequestDto requestDto = new ReservationCreateRequestDto(LocalDate.of(2023, 1, 9), LocalTime.of(1, 30), "name", 1L);
        when(themeRepository.findById(requestDto.getThemeId())).thenReturn(any(Optional.class));
        when(reservationRepository.existsByDateAndTime(any(LocalDate.class), any(LocalTime.class))).thenReturn(true);
        assertThatCode(() -> reservationService.save(requestDto)).isInstanceOf(DuplicateReservationException.class);
    }
}
