package roomservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomservice.domain.dto.ThemeCreateDto;
import roomservice.domain.entity.Theme;
import roomservice.exceptions.exception.ReferencedThemeDeletionException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class ThemeServiceTest {
    Theme defaultTheme = new Theme(1L, "워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
    @Autowired
    ThemeService themeService;
    @Test
    void create(){
        Long givenId = themeService.createTheme(new ThemeCreateDto(
                "test",
                "testDescription",
                15000
        ));
        assertThat(themeService.findThemeById(givenId).getName()).isEqualTo("test");
        assertThat(themeService.findThemeById(givenId).getDesc()).isEqualTo("testDescription");
    }
    @Test
    void find(){
        assertThat(themeService.findThemeById(1).getName()).isEqualTo(defaultTheme.getName());
    }

    @Test
    void cannotDeleteWhenReferencedByReservation(){
        assertThatThrownBy(()->themeService.deleteThemeById(1L))
                .isInstanceOf(ReferencedThemeDeletionException.class);
    }

    @Test
    void delete(){
        themeService.deleteThemeById(2L);
        assertThat(themeService.findThemeById(2L)).isNull();
    }
}
