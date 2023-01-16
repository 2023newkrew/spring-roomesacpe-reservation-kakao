package nextstep.theme.dao;

import nextstep.RoomEscapeWebApplication;
import nextstep.theme.ThemeMock;
import nextstep.theme.entity.Theme;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = RoomEscapeWebApplication.class)
@Transactional
class ThemeDaoTest {
    public static final Long NOT_EXIST_THEME_ID = Long.MAX_VALUE;

    @Autowired
    private ThemeDao themeDao;

    @Test
    @DisplayName("존재하지 않는 테마는 조회할 수 없다.")
    void test1() {
        assertThat(themeDao.findById(NOT_EXIST_THEME_ID)).isEmpty();
    }

    @Test
    @DisplayName("생성된 테마는 조회할 수 있다.")
    void test2() {
        Theme theme = ThemeMock.makeRandomTheme();

        themeDao.insert(theme);
        assertThat(themeDao.findById(theme.getId())).isPresent();
    }

    @Test
    @DisplayName("모든 테마를 조회할 수 있다.")
    void test3() {
        int themeSize = 10;
        List<Theme> themes = ThemeMock.makeRandomTheme(themeSize, ThemeMock.getRandomNames(themeSize));
        themes.forEach(themeDao::insert);
        assertThat(themeDao.findAll()).hasSize(themeSize + 1);
    }

    @Test
    @DisplayName("동일한 이름의 테마를 생성할 수 없다.")
    void test4() {
        String themeName = "theme1";
        Theme theme1 = ThemeMock.makeRandomTheme(themeName);
        Theme theme2 = ThemeMock.makeRandomTheme(themeName);

        themeDao.insert(theme1);
        Assertions.assertThatThrownBy(() -> themeDao.insert(theme2))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("테마 생성 후, id를 설정해야한다.")
    void test5() {
        Theme theme = ThemeMock.makeRandomTheme();

        themeDao.insert(theme);
        assertThat(theme.getId()).isNotEqualTo(0L);
    }

    @Test
    @DisplayName("수정된 테마는 데이터베이스에 반영되어야 한다.")
    void test6() {
        Theme theme1 = ThemeMock.makeRandomTheme();
        themeDao.insert(theme1);

        Theme theme2 = ThemeMock.makeRandomTheme();
        theme2.setId(theme1.getId());

        themeDao.update(theme2);
        Optional<Theme> updatedThemeOptional = themeDao.findById(theme1.getId());
        assertThat(updatedThemeOptional).isPresent();
        Theme updatedTheme = updatedThemeOptional.get();

        assertThat(updatedTheme).isEqualTo(theme2);
    }

    @Test
    @DisplayName("삭제된 테마는 조회할 수 없다.")
    void test7() {
        Theme theme = ThemeMock.makeRandomTheme();
        themeDao.insert(theme);

        themeDao.deleteById(theme.getId());
        assertThat(themeDao.findById(theme.getId())).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 테마는 수정할 수 없다.")
    void test8() {
        Theme theme = ThemeMock.makeRandomTheme();
        theme.setId(NOT_EXIST_THEME_ID);

        int updateCount = themeDao.update(theme);
        assertThat(updateCount).isZero();
    }

    @Test
    @DisplayName("존재하지 않는 테마는 삭제할 수 없다.")
    void test9() {
        int deleteCount = themeDao.deleteById(NOT_EXIST_THEME_ID);
        assertThat(deleteCount).isZero();
    }
}
