package roomescape.service.exception;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import roomescape.dao.reservation.ReservationDAO;
import roomescape.dao.theme.ThemeDAO;
import roomescape.exception.BadRequestException;
import roomescape.service.theme.ThemeService;
import roomescape.service.theme.ThemeServiceInterface;

@DisplayName("테마 서비스 예외 테스트")
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ThemeServiceExceptionTest {

    private static final Long ID_DATA = 1L;
//    private static final String NAME_DATA = "워너고홈";
//    private static final String DESC_DATA = "병맛 어드벤처 회사 코믹물";
//    private static final int PRICE_DATA = 29000;

    private ThemeServiceInterface themeService;

    @Mock
    private ReservationDAO reservationDAO;

    @Mock
    private ThemeDAO themeDAO;

    @BeforeEach
    void setUp() {
        themeService = new ThemeService(reservationDAO, themeDAO);
    }

//    @DisplayName("테마 생성")
//    @Test
//    void createTheme() {
//        Theme theme = new Theme(NAME_DATA, DESC_DATA, PRICE_DATA);
//
//        when(themeDAO.exist(theme)).thenReturn(false);
//        when(themeDAO.create(theme)).thenReturn(ID_DATA);
//
//        assertThat(themeService.create(theme)).isEqualTo(ID_DATA);
//        verify(themeDAO, times(1)).exist(theme);
//        verify(themeDAO, times(1)).create(theme);
//    }
//
//    @DisplayName("테마 목록 조회")
//    @Test
//    void listTheme() {
//        Theme theme = new Theme(NAME_DATA, DESC_DATA, PRICE_DATA);
//
//        when(themeDAO.list()).thenReturn(List.of(theme));
//
//        assertThat(themeService.list()).isEqualTo(List.of(theme));
//        verify(themeDAO, times(1)).list();
//    }

    @DisplayName("테마 삭제) 예약과 관계있는 스케줄 혹은 테마는 수정 및 삭제가 불가능하다.")
    @Test
    void failToRemoveUsedTheme() {
        when(reservationDAO.existThemeId(ID_DATA)).thenReturn(true);
        when(themeDAO.existId(ID_DATA)).thenReturn(true);

        assertThrows(
                BadRequestException.class,
                () -> themeService.remove(ID_DATA));
    }
}
