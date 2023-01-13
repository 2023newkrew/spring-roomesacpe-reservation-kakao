package roomservice.repository;

import net.bytebuddy.dynamic.scaffold.MethodRegistry;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import roomservice.domain.entity.Theme;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class ThemeDaoTest {
    @Autowired
    ThemeDao themeDao;
    Theme theme = new Theme(null, "test", "test", 20000);
    Theme defaultTheme = new Theme(1L, "워너고홈", "병맛 어드벤처 회사 코믹물", 29000);

    @Test
    void create(){
        long givenId = themeDao.createTheme(theme);
        assertThat(themeDao.selectThemeById(givenId).getName()).isEqualTo("test");
        assertThat(themeDao.selectThemeById(givenId).getDesc()).isEqualTo("test");
        assertThat(themeDao.selectThemeById(givenId).getPrice()).isEqualTo(20000);
    }

    @Test
    void selectById(){
        assertThat(themeDao.selectThemeById(1L)).isEqualTo(defaultTheme);
    }

    @Test
    void selectByIdNotFound(){
        System.out.println(themeDao.selectThemeById(0L));
        assertThat(themeDao.selectThemeById(0L)).isNull();
    }

    @Test
    void deleteById(){
        themeDao.createTheme(theme);
        assertThatCode(()->{themeDao.deleteThemeById(theme.getId());})
                .doesNotThrowAnyException();
        assertThat(themeDao.selectThemeById(2L)).isNull();
    }
}
