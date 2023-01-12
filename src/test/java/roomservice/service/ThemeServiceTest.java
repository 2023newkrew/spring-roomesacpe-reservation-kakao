package roomservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomservice.domain.dto.ThemeCreateDto;
import roomservice.domain.entity.Theme;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class ThemeServiceTest {
    Theme defaultTheme = new Theme(1L, "워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
    @Autowired
    ThemeService themeService;
    @Test
    void create(){
        assertThat(themeService.createTheme(new ThemeCreateDto(
                "test",
                "testDescription",
                15000
        ))).isEqualTo(2L);
    }
    @Test
    void find(){
        assertThat(themeService.findThemeById(1)).isEqualTo(defaultTheme);
    }

    @Test
    void delete(){
        themeService.deleteThemeById(1L);
        assertThat(themeService.findThemeById(1L)).isNull();
    }
}
