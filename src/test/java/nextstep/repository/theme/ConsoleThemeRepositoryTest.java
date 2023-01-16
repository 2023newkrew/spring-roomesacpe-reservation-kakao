package nextstep.repository.theme;

import nextstep.domain.Theme;
import nextstep.exception.ReservationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static nextstep.exception.ErrorCode.DUPLICATED_THEME_EXISTS;
import static nextstep.exception.ErrorCode.THEME_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConsoleThemeRepositoryTest {

    ConsoleThemeRepository consoleThemeRepository = new ConsoleThemeRepository();

    Theme theme = new Theme(4L, "테스트 테마", "테스트용 테마임", 1234);

    @AfterEach
    void setUpTable() throws Exception {
        consoleThemeRepository.dropTable();
        consoleThemeRepository.createTable();
    }

    @Test
    void 테마를_저장할_수_있다() {
        assertDoesNotThrow(() -> consoleThemeRepository.save(theme));
    }

    @Test
    void 중복된_이름으로_테마를_만들_수_없다() {
        //when, then
        ReservationException e = assertThrows(ReservationException.class,
                () -> {
                    consoleThemeRepository.save(theme);
                    consoleThemeRepository.save(theme);
                }
        );
        assertThat(e.getErrorCode()).isEqualTo(DUPLICATED_THEME_EXISTS);
    }

    @Test
    void 테마를_조회할_수_있다() {
        //given
        Long saveId = consoleThemeRepository.save(theme);

        //when
        Theme foundTheme = consoleThemeRepository.findById(saveId);

        //then
        assertThat(theme.getName()).isEqualTo(foundTheme.getName());
        assertThat(theme.getDesc()).isEqualTo(foundTheme.getDesc());
        assertThat(theme.getPrice()).isEqualTo(foundTheme.getPrice());
    }

    @Test
    void 모든_테마들을_조회할_수_있다() {
        //given
        Theme theme2 = new Theme(5L, "test", "test theme", 30000);
        consoleThemeRepository.save(theme);
        consoleThemeRepository.save(theme2);

        //when
        List<Theme> themes = consoleThemeRepository.findAll();

        //then
        assertThat(themes.size()).isEqualTo(2);
    }

    @Test
    void 테마가_없을_때_모든_테마들을_조회할_수_없다() {
        ReservationException e = assertThrows(ReservationException.class, () -> consoleThemeRepository.findAll());
        assertThat(e.getErrorCode()).isEqualTo(THEME_NOT_FOUND);
    }

    @Test
    void 없는_테마를_조회할_수_없다() {
        //given
        Long fakeId = 1L;

        //when, then
        ReservationException e = assertThrows(ReservationException.class,
                () -> consoleThemeRepository.findById(fakeId));
        assertThat(e.getErrorCode()).isEqualTo(THEME_NOT_FOUND);
    }

    @Test
    void 테마를_삭제할_수_있다() {
        //given
        Long saveId = consoleThemeRepository.save(theme);

        //when, then
        assertDoesNotThrow(() -> consoleThemeRepository.deleteById(saveId));
        assertThatThrownBy(() -> consoleThemeRepository.findById(saveId))
                .isInstanceOf(ReservationException.class);
    }

    @Test
    void 없는_테마를_삭제할_수_없다() {
        //given
        Long fakeId = 1L;

        //when, then
        ReservationException e = assertThrows(ReservationException.class,
                () -> consoleThemeRepository.deleteById(fakeId));
        assertThat(e.getErrorCode()).isEqualTo(THEME_NOT_FOUND);
    }

}