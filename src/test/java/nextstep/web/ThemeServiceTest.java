package nextstep.web;

import nextstep.exception.ThemeNotFoundException;
import nextstep.service.ThemeService;
import nextstep.web.JdbcTemplateThemeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
public class ThemeServiceTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private ThemeService themeService;

    @BeforeEach
    void setUp() {
        themeService = new ThemeService(new JdbcTemplateThemeRepository(jdbcTemplate));
    }

    @DisplayName("테마를 찾을 수 없으면 예외가 발생한다")
    @Test
    void getNotFoundTheme() {
        Long id = 12314L;

        Assertions.assertThatThrownBy(() -> themeService.getTheme(id))
                        .isInstanceOf(ThemeNotFoundException.class);
    }
}
