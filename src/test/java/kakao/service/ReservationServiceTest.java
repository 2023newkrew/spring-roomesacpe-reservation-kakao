package kakao.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import kakao.domain.Reservation;
import kakao.domain.Theme;
import kakao.dto.request.CreateReservationRequest;
import kakao.dto.response.ReservationResponse;
import kakao.error.ErrorCode;
import kakao.error.exception.CustomException;
import kakao.repository.reservation.ReservationRepository;
import kakao.repository.theme.ThemeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("예약 도메인 서비스 레이어 테스트")
class ReservationServiceTest {
    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ThemeRepository themeRepository;

    @InjectMocks
    private ReservationService reservationService;

    Theme theme = Theme.builder()
            .id(1L)
            .name("test theme")
            .desc("plz play test theme.")
            .price(20000)
            .build();

    Reservation reservation = Reservation.builder()
            .date(LocalDate.of(2022, Month.JANUARY, 12))
            .time(LocalTime.of(15, 0))
            .name("test")
            .theme(theme)
            .build();

    @Nested
    @DisplayName("예약 생성 테스트")
    class ReservationCreateTest {
        @Test
        @DisplayName("정상적으로 예약을 생성할 수 있다.")
        public void testCreateReservation() {
            //given
            CreateReservationRequest request = new CreateReservationRequest(
                    reservation.getDate(),
                    reservation.getTime(),
                    reservation.getName(),
                    theme.getId()
            );
            doReturn(List.of()).when(reservationRepository)
                    .findByThemeIdAndDateAndTime(request.themeId, request.date, request.time);
            doReturn(theme).when(themeRepository).findById(request.themeId);
            doReturn(reservation).when(reservationRepository).save(Mockito.any(Reservation.class));

            //when
            ReservationResponse response = reservationService.createReservation(request);

            //then
            verify(reservationRepository)
                    .findByThemeIdAndDateAndTime(request.themeId, request.date, request.time);
            verify(themeRepository).findById(request.themeId);
            verify(reservationRepository).save(Mockito.any(Reservation.class));
            assertThat(response).hasFieldOrPropertyWithValue("id", reservation.getId())
                    .hasFieldOrPropertyWithValue("date", reservation.getDate())
                    .hasFieldOrPropertyWithValue("time", reservation.getTime())
                    .hasFieldOrPropertyWithValue("name", reservation.getName());
            assertThat(response.theme).hasFieldOrPropertyWithValue("id", theme.getId())
                    .hasFieldOrPropertyWithValue("name", theme.getName())
                    .hasFieldOrPropertyWithValue("desc", theme.getDesc())
                    .hasFieldOrPropertyWithValue("price", theme.getPrice());
        }

        @Test
        @DisplayName("잘못된 테마의 ID로는 예약을 생성할 수 없다.")
        public void testCreateReservationWithInvalidThemeId() {
            //given
            CreateReservationRequest request = new CreateReservationRequest(
                    reservation.getDate(),
                    reservation.getTime(),
                    reservation.getName(),
                    0L
            );
            doReturn(List.of()).when(reservationRepository)
                    .findByThemeIdAndDateAndTime(request.themeId, request.date, request.time);
            doReturn(null).when(themeRepository).findById(0L);

            //when //then
            assertThatThrownBy(() -> reservationService.createReservation(request))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(ErrorCode.THEME_NOT_FOUND.getMessage());
            verify(reservationRepository)
                    .findByThemeIdAndDateAndTime(request.themeId, request.date, request.time);
            verify(themeRepository).findById(0L);
        }

        @Test
        @DisplayName("같은 테마 중복된 시간에는 예약을 할 수 없다.")
        public void testCreateDuplicatedReservation() {
            //given
            CreateReservationRequest request = new CreateReservationRequest(
                    reservation.getDate(),
                    reservation.getTime(),
                    reservation.getName(),
                    0L
            );
            doReturn(List.of(reservation)).when(reservationRepository)
                    .findByThemeIdAndDateAndTime(request.themeId, request.date, request.time);

            //when //then
            assertThatThrownBy(() -> reservationService.createReservation(request))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(ErrorCode.DUPLICATE_RESERVATION.getMessage());
            verify(reservationRepository)
                    .findByThemeIdAndDateAndTime(request.themeId, request.date, request.time);
        }
    }

    @Test
    @DisplayName("ID로 예약을 조회할 수 있다.")
    public void testGetReservationById() {
        //given
        doReturn(reservation).when(reservationRepository).findById(reservation.getId());

        //when
        ReservationResponse response = reservationService.getReservation(reservation.getId());

        //then
        verify(reservationRepository).findById(reservation.getId());
        assertThat(response).hasFieldOrPropertyWithValue("id", reservation.getId())
                .hasFieldOrPropertyWithValue("date", reservation.getDate())
                .hasFieldOrPropertyWithValue("time", reservation.getTime())
                .hasFieldOrPropertyWithValue("name", reservation.getName());
        assertThat(response.theme).hasFieldOrPropertyWithValue("id", theme.getId())
                .hasFieldOrPropertyWithValue("name", theme.getName())
                .hasFieldOrPropertyWithValue("desc", theme.getDesc())
                .hasFieldOrPropertyWithValue("price", theme.getPrice());
    }

    @Test
    @DisplayName("ID로 예약을 삭제할 수 있다.")
    public void testDeleteReservationById() {
        //given
        doReturn(1).when(reservationRepository).delete(reservation.getId());

        //when
        int deletedCount = reservationService.deleteReservation(reservation.getId());

        //then
        verify(reservationRepository).delete(reservation.getId());
        assertThat(deletedCount).isEqualTo(1);
    }


}