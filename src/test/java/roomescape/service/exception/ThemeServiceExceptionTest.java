package roomescape.service.exception;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import roomescape.dao.reservation.ReservationDAO;
import roomescape.dao.theme.ThemeDAO;
import roomescape.dto.Theme;
import roomescape.exception.BadRequestException;
import roomescape.service.theme.ThemeService;
import roomescape.service.theme.ThemeServiceInterface;

@DisplayName("테마 서비스 예외 테스트")
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ThemeServiceExceptionTest {

    private static final Long ID_DATA = 1L;
    private static final String NAME_DATA = "워너고홈";
    private static final String DESC_DATA = "병맛 어드벤처 회사 코믹물";
    private static final int PRICE_DATA = 29000;

    private ThemeServiceInterface themeService;

    @Mock
    private ReservationDAO reservationDAO;

    @Mock
    private ThemeDAO themeDAO;

    @BeforeEach
    void setUp() {
        themeService = new ThemeService(reservationDAO, themeDAO);
    }

    @DisplayName("테마 생성) 같은 이름의 예약은 생성 불가")
    @Test
    void failToCreateSameName() {
        Theme theme = new Theme(NAME_DATA, DESC_DATA, PRICE_DATA);

        when(themeDAO.exist(theme)).thenReturn(true);

        assertThrows(
                BadRequestException.class,
                () -> themeService.create(theme));
    }

    @DisplayName("테마 생성) 값이 포함되지 않았을 경우 생설 불가")
    @ParameterizedTest
    @MethodSource("getFailToCreateInvalidValueData")
    void failToCreateInvalidValue(Theme theme) {
        assertThrows(
                BadRequestException.class,
                () -> themeService.create(theme));
    }

    private static Stream<Arguments> getFailToCreateInvalidValueData() {
        return Stream.of(
                Arguments.arguments(new Theme(null, DESC_DATA, PRICE_DATA)),
                Arguments.arguments(new Theme(NAME_DATA, null, PRICE_DATA)),
                Arguments.arguments(new Theme(NAME_DATA, DESC_DATA, null))
        );
    }

    @DisplayName("테마 조회) ID가 없는 경우 조회 불가")
    @Test
    void failToFindNotExistingId() {
        when(themeDAO.find(ID_DATA)).thenReturn(null);

        assertThatThrownBy(() -> themeService.find(ID_DATA))
                .isInstanceOf(BadRequestException.class);
    }

    @DisplayName("테마 삭제) 예약과 관계있는 테마 삭제 불가")
    @Test
    void failToRemoveAlreadyUsed() {
        when(reservationDAO.existThemeId(ID_DATA)).thenReturn(true);
        when(themeDAO.existId(ID_DATA)).thenReturn(true);

        assertThatThrownBy(() -> themeService.remove(ID_DATA))
                .isInstanceOf(BadRequestException.class);
    }

    @DisplayName("테마 삭제) ID가 없는 경우 삭제 불가")
    @Test
    void failToRemoveNotExistingId() {
        when(themeDAO.existId(ID_DATA)).thenReturn(false);

        assertThatThrownBy(() -> themeService.remove(ID_DATA))
                .isInstanceOf(BadRequestException.class);
    }
}
