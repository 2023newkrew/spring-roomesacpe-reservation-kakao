package roomescape.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.domain.Reservation;
import roomescape.domain.Theme;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;
import roomescape.mapper.ReservationMapper;
import roomescape.repository.ReservationWebRepository;
import roomescape.repository.ThemeWebRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    @InjectMocks private ReservationService sut;
    @Mock private ReservationWebRepository reservationWebRepository;
    @Mock private ThemeWebRepository themeWebRepository;

    @DisplayName("[RESERVATION][CREATE] 예약 정상 생성")
    @Test
    void createReservationSuccess(){
        //given
        ReservationRequest reservationRequest =
                new ReservationRequest(LocalDate.of(2023, 1, 1), LocalTime.of(13, 00), "jacob", 1L);
        Theme theme = new Theme(1L, "theme1", "theme1", 22000);
        given(reservationWebRepository.findReservationByDateAndTimeAndTheme(reservationRequest.getDate(), reservationRequest.getTime(), reservationRequest.getTheme_id()))
                .willReturn(Optional.empty());
        given(themeWebRepository.findOne(reservationRequest.getTheme_id()))
                .willReturn(Optional.of(theme));

        //when
        sut.createReservation(reservationRequest);

        //then
        then(reservationWebRepository).should()
                .save(ReservationMapper.INSTANCE.reservationRequestToReservation(reservationRequest));
    }

    @DisplayName("[RESERVATION][CREATE] 예약 생성시 중복된 예약이 존재하면 예외 반환")
    @Test
    void createDuplicatedReservationFail(){
        //given
        ReservationRequest reservationRequest =
                new ReservationRequest(LocalDate.of(2023, 1, 1), LocalTime.of(13, 00), "jacob", 1L);
        Reservation alreadyExistReservation =
                new Reservation(1L, LocalDate.of(2023, 1, 1), LocalTime.of(13, 00), "someone", 1L);

        given(reservationWebRepository.findReservationByDateAndTimeAndTheme(reservationRequest.getDate(), reservationRequest.getTime(), reservationRequest.getTheme_id()))
                .willReturn(Optional.of(alreadyExistReservation));

        //when
        Throwable throwable = catchThrowable(() -> sut.createReservation(reservationRequest));

        //then
        assertThat(throwable).isInstanceOf(RoomEscapeException.class);
        assertThat(((RoomEscapeException)throwable).getHttpStatus())
                .isEqualTo(ErrorCode.DUPLICATED_RESERVATION.getHttpStatus());
        assertThat(throwable.getMessage())
                .isEqualTo(ErrorCode.DUPLICATED_RESERVATION.getMessage());
    }

    @DisplayName("[RESERVATION][CREATE] 존재하지 않은 테마에 대해 예약 생성 시도시 예외 반환")
    @Test
    void createReservationToNotExistThemeFail(){
        //given
        ReservationRequest reservationRequest =
                new ReservationRequest(LocalDate.of(2023, 1, 1), LocalTime.of(13, 00), "jacob", 1L);

        given(reservationWebRepository.findReservationByDateAndTimeAndTheme(reservationRequest.getDate(), reservationRequest.getTime(), reservationRequest.getTheme_id()))
                .willReturn(Optional.empty());
        given(themeWebRepository.findOne(reservationRequest.getTheme_id()))
                .willReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> sut.createReservation(reservationRequest));

        //then
        assertThat(throwable).isInstanceOf(RoomEscapeException.class);
        assertThat(((RoomEscapeException)throwable).getHttpStatus())
                .isEqualTo(ErrorCode.THEME_NOT_FOUND.getHttpStatus());
        assertThat(throwable.getMessage())
                .isEqualTo(ErrorCode.THEME_NOT_FOUND.getMessage());
    }

    @DisplayName("[RESERVATION][GET] 예약 정상 조회")
    @Test
    void getReservationSuccess(){
        //given
        Theme theme = new Theme(1L, "theme1", "theme1", 22000);
        Reservation reservation =
                new Reservation(1L, LocalDate.of(2023, 1, 1), LocalTime.of(13, 00), "someone", theme.getId());

        given(reservationWebRepository.findOne(reservation.getId()))
                .willReturn(Optional.of(reservation));
        given(themeWebRepository.findOne(theme.getId()))
                .willReturn(Optional.of(theme));

        //when
        ReservationResponse reservationResponse = sut.getReservation(reservation.getId());

        //then
        assertThat(reservationResponse)
                .isEqualTo(ReservationMapper.INSTANCE.reservationToReservationResponse(reservation, theme));
    }

    @DisplayName("[RESERVATION][GET] 존재하지 않는 예약 조회시 예외 반환")
    @Test
    void getNotExistReservationFail(){
        //given
        Long reservationId = 1L;

        given(reservationWebRepository.findOne(reservationId))
                .willReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> sut.getReservation(reservationId));

        //then
        assertThat(throwable).isInstanceOf(RoomEscapeException.class);
        assertThat(((RoomEscapeException)throwable).getHttpStatus())
                .isEqualTo(ErrorCode.RESERVATION_NOT_FOUND.getHttpStatus());
        assertThat(throwable.getMessage())
                .isEqualTo(ErrorCode.RESERVATION_NOT_FOUND.getMessage());
    }

    @DisplayName("[RESERVATION][GET] 해당 예약에 대한 테마가 존재하지 않을 시 예외 반환")
    @Test
    void getReservationWithNoThemeFail(){
        //given
        Long themeId = 1L;
        Reservation reservation =
                new Reservation(1L, LocalDate.of(2023, 1, 1), LocalTime.of(13, 00), "someone", themeId);

        given(reservationWebRepository.findOne(reservation.getId()))
                .willReturn(Optional.of(reservation));
        given(themeWebRepository.findOne(themeId))
                .willReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> sut.getReservation(reservation.getId()));

        //then
        assertThat(throwable).isInstanceOf(RoomEscapeException.class);
        assertThat(((RoomEscapeException)throwable).getHttpStatus())
                .isEqualTo(ErrorCode.THEME_NOT_FOUND.getHttpStatus());
        assertThat(throwable.getMessage())
                .isEqualTo(ErrorCode.THEME_NOT_FOUND.getMessage());
    }

    @DisplayName("[RESERVATION][DELETE] 예약 정상 삭제")
    @Test
    void deleteReservationSuccess(){
        //given
        Reservation reservation =
                new Reservation(1L, LocalDate.of(2023, 1, 1), LocalTime.of(13, 00), "someone", 1L);

        //when
        sut.deleteReservation(reservation.getId());

        //then
        then(reservationWebRepository).should()
                .delete(reservation.getId());
    }
}
