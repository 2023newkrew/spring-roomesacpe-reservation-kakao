package reservation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reservation.model.domain.Reservation;
import reservation.model.domain.Theme;
import reservation.model.dto.RequestReservation;
import reservation.respository.ReservationJdbcTemplateRepository;
import reservation.respository.ThemeJdbcTemplateRepository;
import reservation.service.ReservationService;
import reservation.util.exception.restAPI.DuplicateException;
import reservation.util.exception.restAPI.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationJdbcTemplateRepository reservationRepository;
    @Mock
    private ThemeJdbcTemplateRepository themeRepository;
    private final RequestReservation req;
    private final Reservation reservation;

    public ServiceTest() {
        LocalDate date = LocalDate.of(2023, 1, 1);
        LocalTime time = LocalTime.of(11, 0);
        this.req = new RequestReservation(date, time, "name", 1L);
        this.reservation = new Reservation(0L, date, time, "name", 1L);
    }

    @Test
    @DisplayName("예약을 생성할 수 있다.")
    void saveTest(){
        // when
        given(reservationRepository.save(any()))
                .willReturn(1L);

        assertThat(reservationRepository.save(reservation)).isEqualTo(1L);
    }

    @Test
    @DisplayName("날짜와 시간이 중복되는 예약 생성은 불가능하다.")
    void saveDuplicateTest(){
        // 무조건 중복되었다고 반환
        given(reservationRepository.existByDateTimeTheme(any(), any(), any()))
                .willReturn(true);
        // 무조건 존재한다고 반환
        given(themeRepository.checkExistById(any()))
                .willReturn(true);

        // 중복 반환 시 DuplicateException 발생
        assertThatExceptionOfType(DuplicateException.class)
                .isThrownBy(() -> reservationService.createReservation(req));
    }

    @Test
    @DisplayName("검색을 원하는 id 값을 가지는 예약이 없다면 검색이 불가능하다.")
    void findByIdNotFoundTest(){
        // 무조건 해당 id 값을 가지는 예약이 없다고 반환
        given(reservationRepository.existById(any()))
                .willReturn(false);

        // 예약 조회 불가능
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> reservationService.getReservation(1L));
    }

    @Test
    @DisplayName("삭제를 원하는 id 값을 가지는 예약이 없다면 삭제가 불가능하다.")
    void deleteByIdNotFoundTest(){
        // 무조건 해당 id 값을 가지는 예약이 없다고 반환
        given(reservationRepository.existById(any()))
                .willReturn(false);

        // 예약 삭제 불가능
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> reservationService.deleteReservation(1L));
    }
}
