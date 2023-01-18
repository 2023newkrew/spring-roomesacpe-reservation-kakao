package reservation.theme;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reservation.model.domain.Theme;
import reservation.model.dto.RequestTheme;
import reservation.respository.ReservationJdbcTemplateRepository;
import reservation.respository.ThemeJdbcTemplateRepository;
import reservation.service.ThemeService;
import reservation.util.exception.restAPI.DuplicateException;
import reservation.util.exception.restAPI.ExistException;
import reservation.util.exception.restAPI.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @InjectMocks
    private ThemeService themeService;

    @Mock
    private ReservationJdbcTemplateRepository reservationRepository;
    @Mock
    private ThemeJdbcTemplateRepository themeRepository;
    private final RequestTheme req;

    public ServiceTest() {
        this.req = new RequestTheme("name", "desc", 10000);
    }

    @Test
    @DisplayName("테마를 생성할 수 있다.")
    void saveTest(){
        given(themeRepository.save(any()))
                .willReturn(1L);

        assertThat(themeService.createTheme(req)).isEqualTo(1L);
    }

    @Test
    @DisplayName("이미 존재하는 테마는 생성이 불가능하다.")
    void saveDuplicateTest(){
        // 무조건 존재한다고 반환
        given(themeRepository.checkDuplicateName(any()))
                .willReturn(true);

        // 중복 반환 시 DuplicateException 발생
        assertThatExceptionOfType(DuplicateException.class)
                .isThrownBy(() -> themeService.createTheme(req));
    }

    @Test
    @DisplayName("해당 테마를 가지고 있는 예약이 있다면 삭제가 불가능하다.")
    void deleteByIdReservationExist(){
        // 무조건 해당 id 값을 가지는 예약이 있다고 반환
        given(reservationRepository.existByThemeId(any()))
                .willReturn(true);

        // 테마는 무조건 존재한다고 반환
        given(themeRepository.checkExistById(any()))
                .willReturn(true);

        // 테마 삭제 불가능
        assertThatExceptionOfType(ExistException.class)
                .isThrownBy(() -> themeService.deleteTheme(1L));
    }

    @Test
    @DisplayName("삭제를 원하는 id 값을 가지는 테마가 없다면 삭제가 불가능하다.")
    void deleteByIdNotFoundTest(){
        // 무조건 해당 id 값을 가지는 테마가 없다고 반환
        given(themeRepository.checkExistById(any()))
                .willReturn(false);

        // 예약 삭제 불가능
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> themeService.deleteTheme(1L));
    }
}