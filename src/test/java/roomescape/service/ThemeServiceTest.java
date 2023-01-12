package roomescape.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.ThemeRequestDto;

@SpringBootTest
public class ThemeServiceTest {
    @Autowired
    private ThemeService themeService;

    @DisplayName("Theme 저장 후 조회 가능함을 확인")
    @Test
    @Transactional
    public void createAndFindThemeTest() {
        //given
        ThemeRequestDto themeRequestDto = new ThemeRequestDto("Test Theme", "Lorem Ipsum", 1000);
        //when
        Long themeId = themeService.createTheme(themeRequestDto);
        //then
        Assertions.assertThatNoException().isThrownBy(() -> themeService.findTheme(themeId));
    }

    @DisplayName("중복된 이름의 theme 저장 시 예외 발생")
    @Test
    @Transactional
    public void createDuplicateThemeTest() {
        //given
        ThemeRequestDto themeRequestDto = new ThemeRequestDto("Test Theme", "Lorem Ipsum", 1000);
        //when
        Long themeId = themeService.createTheme(themeRequestDto);
        //then
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> themeService.createTheme(themeRequestDto));
    }
}
