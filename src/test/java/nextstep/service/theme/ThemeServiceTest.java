package nextstep.service.theme;

import nextstep.domain.Theme;
import nextstep.service.ThemeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ThemeServiceTest {

    @Autowired
    ThemeService themeService;

    static Theme theme;

    @BeforeAll
    static void setUpTheme() {
        theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
    }

    @DisplayName("테마를 생성 할 수 있다.")
    @Test
    void createThemeTest() {
        assertDoesNotThrow(() -> themeService.createTheme(theme));
    }

    @DisplayName("테마를 삭제 할 수 있다.")
    @Test
    void deleteThemeTest() {
        themeService.createTheme(theme);
        Long id = themeService.findByTheme(theme).getId();
        assertDoesNotThrow(() -> themeService.deleteById(id));
    }

    @DisplayName("같은 테마를 중복으로 생성 할 수 없다.")
    @Test
    void duplicateThemeExceptionTest() {
        themeService.createTheme(theme);
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> themeService.createTheme(theme));
    }
}
