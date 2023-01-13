package nextstep.service;

import nextstep.domain.dto.theme.CreateThemeDto;
import nextstep.domain.dto.theme.UpdateThemeDto;
import nextstep.domain.theme.Theme;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest
@DisplayName("Theme Service 테스트")
@Sql(scripts = {"classpath:recreate.sql"})
public class ThemeServiceTest {

    private final ThemeService themeService;
    private static final CreateThemeDto createThemeDto = new CreateThemeDto(
            "카페 라떼",
            "LIH 바이러스 위기에서 탈출",
            32000
    );

    @Autowired
    public ThemeServiceTest(ThemeService themeService) {
        this.themeService = themeService;
    }

    @DisplayName("예외 발생 없이 테마 추가 성공")
    @Test
    void addTheme() {
        assertThatNoException()
                .isThrownBy(() -> themeService.addTheme(createThemeDto));
    }

    @DisplayName("전체 테마 조회, 기존 1개 + 3개 추 = 4개 조회")
    @Test
    void findAllThemes() {
       themeService.addTheme(createThemeDto);
       themeService.addTheme(createThemeDto);
       themeService.addTheme(createThemeDto);

       assertThat(themeService.getAllThemes())
               .hasSize(4);
    }

    @DisplayName("예외 발생 없이 테마 업데이트 성공")
    @Test
    void updateTheme() {
        UpdateThemeDto updateThemeDto = new UpdateThemeDto(
                1l,
                "new name",
                "new desc",
                20000
        );

        assertThatNoException()
                .isThrownBy(() -> themeService.updateTheme(updateThemeDto));
    }

    @DisplayName("예외 발생 없이 테마 삭제 성공")
    @Test
    void deleteTheme() {
        assertThatNoException()
                .isThrownBy(() -> themeService.deleteTheme(1l));
    }

}
