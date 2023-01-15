package nextstep.repository.theme;

import nextstep.domain.Theme;
import nextstep.exception.ReservationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static nextstep.exception.ErrorCode.DUPLICATED_THEME_EXISTS;
import static nextstep.exception.ErrorCode.THEME_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class JdbcThemeRepositoryTest {

    @Autowired
    JdbcThemeRepository jdbcThemeRepository;

    @AfterEach
    void setUp() {
        jdbcThemeRepository.dropTable();
        jdbcThemeRepository.createTable();
    }

    @Test
    void 테마를_저장할_수_있다() {
        //given
        Theme theme = new Theme("Bryan's Theme", "This is Bryan's Theme. Enjoy the game.", 10000);

        //when, then
        assertDoesNotThrow(() -> jdbcThemeRepository.save(theme));
    }

    @Test
    void 중복된_이름으로_테마를_만들_수_없다() {
        //given
        Theme theme = new Theme("Bryan's Theme", "This is Bryan's Theme. Enjoy the game.", 10000);

        //when, then
        ReservationException e = assertThrows(ReservationException.class,
                () -> {
                    jdbcThemeRepository.save(theme);
                    jdbcThemeRepository.save(theme);
                }
        );
        assertThat(e.getErrorCode()).isEqualTo(DUPLICATED_THEME_EXISTS);
    }

    @Test
    void 테마를_조회할_수_있다() {
        //given
        Theme theme = new Theme("Bryan's Theme", "This is Bryan's Theme. Enjoy the game.", 10000);

        Long saveId = jdbcThemeRepository.save(theme);
        System.out.println("saveId = " + saveId);

        //when
        Theme foundTheme = jdbcThemeRepository.findById(saveId);

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
                () -> jdbcThemeRepository.findById(fakeId));
        assertThat(e.getErrorCode()).isEqualTo(THEME_NOT_FOUND);
    }

    @Test
    void 테마를_삭제할_수_있다() {
        //given
        Theme theme = new Theme("Bryan's Theme", "This is Bryan's Theme. Enjoy the game.", 10000);

        Long saveId = jdbcThemeRepository.save(theme);

        //when, then
        assertDoesNotThrow(() -> jdbcThemeRepository.deleteById(saveId));
        assertThatThrownBy(() -> jdbcThemeRepository.findById(saveId))
                .isInstanceOf(ReservationException.class);
    }

    @Test
    void 없는_테마를_삭제할_수_없다() {
        //given
        Long fakeId = 1L;

        //when, then
        ReservationException e = assertThrows(ReservationException.class,
                () -> jdbcThemeRepository.deleteById(fakeId));
        assertThat(e.getErrorCode()).isEqualTo(THEME_NOT_FOUND);
    }

}