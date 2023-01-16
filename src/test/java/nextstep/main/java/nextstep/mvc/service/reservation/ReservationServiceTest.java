package nextstep.main.java.nextstep.mvc.service.reservation;

import nextstep.main.java.nextstep.global.exception.exception.DuplicateReservationException;
import nextstep.main.java.nextstep.global.exception.exception.NoSuchReservationException;
import nextstep.main.java.nextstep.mvc.domain.reservation.Reservation;
import nextstep.main.java.nextstep.mvc.domain.reservation.ReservationMapper;
import nextstep.main.java.nextstep.mvc.domain.reservation.request.ReservationCreateRequest;
import nextstep.main.java.nextstep.mvc.domain.reservation.response.ReservationFindResponse;
import nextstep.main.java.nextstep.mvc.domain.theme.Theme;
import nextstep.main.java.nextstep.mvc.repository.CrudRepository;
import nextstep.main.java.nextstep.mvc.repository.reservation.ReservationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;
    @Spy
    private ReservationMapper reservationMapper;
    @Mock
    private ReservationRepository reservationRepository;

    @Test
    @DisplayName("[예약 생성] 예약 생성 성공")
    void saveSuccess() {
        Long id = 1L;
        ReservationCreateRequest request = getCreateRequest();
        Reservation reservation = createReservationFromIdAndRequest(id, request);

        given(reservationRepository.save(any()))
                .willReturn(id);
        given(reservationRepository.findById(id))
                .willReturn(Optional.ofNullable(reservation));

        Long newReservationId = reservationService.save(request);
        ReservationFindResponse response = reservationService.findById(newReservationId);

        assertThat(response.getId()).isEqualTo(reservation.getId());
        assertThat(response.getDate()).isEqualTo(reservation.getDate());
        assertThat(response.getTime()).isEqualTo(reservation.getTime());
        assertThat(response.getName()).isEqualTo(reservation.getName());
        assertThat(response.getThemeDesc()).isEqualTo(reservation.getTheme().getDesc());
    }

    @Test
    @DisplayName("[예약 생성] 일자가 같은 예약은 생성 불가")
    void saveDuplicated() {
        ReservationCreateRequest request = getCreateRequest();

        given(reservationRepository.existsByDateAndTime(any(), any()))
                .willReturn(true);

        assertThatThrownBy(() -> reservationService.save(request))
                .isInstanceOf(DuplicateReservationException.class);
    }

    @Test
    @DisplayName("[예약 조회] 존재하지 않는 예약은 조회 불가")
    void findNotExists() {
        Long id = 1L;
        given(reservationRepository.findById(id))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> reservationService.findById(id))
                .isInstanceOf(NoSuchReservationException.class);
    }

    @Test
    @DisplayName("[예약 삭제] 존재하지 않는 예약은 삭제 불가")
    void deleteNotExists() {
        Long id = 1L;
        given(reservationRepository.existsById(id))
                .willReturn(false);

        assertThatThrownBy(() -> reservationService.deleteById(id))
                .isInstanceOf(NoSuchReservationException.class);
    }

    private Reservation createReservationFromIdAndRequest(Long id, ReservationCreateRequest request) {
        return Reservation.builder()
                .id(id)
                .date(request.getDate())
                .time(request.getTime())
                .name(request.getName())
                .theme(new Theme(id, "theme", "dedscription of theme", 50000))
                .build();
    }

    private ReservationCreateRequest getCreateRequest() {
        return ReservationCreateRequest.of(
                LocalDate.of(2023, 1, 19),
                LocalTime.of(3, 30),
                "test",
                "test");
    }
}
