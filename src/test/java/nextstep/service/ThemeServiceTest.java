package nextstep.service;

import nextstep.domain.Theme;
import nextstep.exception.ReservationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static nextstep.exception.ErrorCode.DUPLICATED_THEME_EXISTS;
import static nextstep.exception.ErrorCode.THEME_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class ThemeServiceTest {

    @Autowired
    ThemeService themeService;
    Theme theme = new Theme(4L, "테스트 테마", "테스트용 테마임", 1234);

    @Test
    void 테마를_저장할_수_있다() {
        assertDoesNotThrow(() -> themeService.createTheme(theme));
    }

    @Test
    void 중복된_이름으로_테마를_만들_수_없다() {
        //when, then
        ReservationException e = assertThrows(ReservationException.class,
                () -> {
                    themeService.createTheme(theme);
                    themeService.createTheme(theme);
                }
        );
        assertThat(e.getErrorCode()).isEqualTo(DUPLICATED_THEME_EXISTS);
    }

    @Test
    void 테마를_조회할_수_있다() {
        //given
        Long saveId = themeService.createTheme(theme);

        //when
        Theme foundTheme = themeService.findById(saveId);

        //then
        assertThat(theme.getName()).isEqualTo(foundTheme.getName());
        assertThat(theme.getDesc()).isEqualTo(foundTheme.getDesc());
        assertThat(theme.getPrice()).isEqualTo(foundTheme.getPrice());
    }

    @Test
    void 없는_테마를_조회할_수_없다() {
        //given
        Long fakeId = 1L;

        //when, then
        ReservationException e = assertThrows(ReservationException.class,
                () -> themeService.findById(fakeId));
        assertThat(e.getErrorCode()).isEqualTo(THEME_NOT_FOUND);
    }

    @Test
    void 테마를_삭제할_수_있다() {
        //given
        Long saveId = themeService.createTheme(theme);

        //when, then
        assertDoesNotThrow(() -> themeService.deleteTheme(saveId));
        ReservationException e = assertThrows(ReservationException.class,
                () -> themeService.findById(saveId));
        assertThat(e.getErrorCode()).isEqualTo(THEME_NOT_FOUND);
    }

    @Test
    void 없는_테마를_삭제할_수_없다() {
        //given
        Long fakeId = 1L;

        //when, then
        ReservationException e = assertThrows(ReservationException.class,
                () -> themeService.deleteTheme(fakeId));
        assertThat(e.getErrorCode()).isEqualTo(THEME_NOT_FOUND);
    }

}