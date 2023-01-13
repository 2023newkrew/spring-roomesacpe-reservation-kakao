package nextstep.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import nextstep.domain.Theme;
import nextstep.exceptions.ErrorCode;
import nextstep.exceptions.ThemeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@DisplayName("Theme Service")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ThemeServiceTest {
    @Autowired
    private ThemeService themeService;

    private Theme theme;

    @BeforeEach
    void setUp() {
        theme = new Theme("테마이름", "테마설명", 22000);
    }

    @Test
    @DisplayName("동일한 이름을 가진 테마가 존재할 경우 예외 처리 테스트")
    public void themeAlreadyExistsTest() {
        themeService.createTheme(theme);
        assertThatThrownBy(() -> themeService.createTheme(theme))
                .isInstanceOf(ThemeException.class)
                .hasMessage(ErrorCode.ALREADY_THEME_EXISTS.getMessage());
    }

    @Test
    @DisplayName("테마 삭제 시 테마가 없을 경우 예외 처리 테스트")
    public void noViewedThemeTest() {
        assertThatThrownBy(() -> themeService.deleteTheme(1L))
                .isInstanceOf(ThemeException.class)
                .hasMessage(ErrorCode.THEME_NOT_FOUND.getMessage());
    }
}
