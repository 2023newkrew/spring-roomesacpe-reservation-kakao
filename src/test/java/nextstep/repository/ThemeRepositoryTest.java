package nextstep.repository;

import nextstep.domain.Theme;
import nextstep.domain.repository.ThemeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class ThemeRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ThemeRepository themeRepository;

    @BeforeEach
    void setup() {

    }

    @Test
    void 테마_생성() {
        // given
        Theme theme = new Theme("테마2", "지금까지 이런 테마는 없었다.", 20200);

        // when
        Long id = themeRepository.save(theme);

        // then
        assertThat(id).isNotZero();
    }

    @Test
    void id로_테마_조회() {
        // given
        Theme theme = new Theme("테마2", "지금까지 이런 테마는 없었다.", 20200);
        Long id = themeRepository.save(theme);

        // when
        Optional<Theme> savedTheme = themeRepository.findThemeById(id);

        // then
        assertThat(savedTheme).isNotEqualTo(Optional.empty());
        assertThat(savedTheme.get()).isEqualTo(theme);
    }

    @Test
    void 모든_테마_조회() {
        // given
        jdbcTemplate.execute("DELETE FROM reservation");
        jdbcTemplate.execute("DELETE FROM theme");
        Theme theme = new Theme("테마2", "지금까지 이런 테마는 없었다.", 20200);
        themeRepository.save(theme);
        themeRepository.save(theme);
        themeRepository.save(theme);
        themeRepository.save(theme);
        themeRepository.save(theme);

        // when
        List<Theme> themes = themeRepository.getAllThemes();

        // then
        assertThat(themes.size()).isEqualTo(5);
    }

    @Test
    void id로_테마_삭제() {
        // given
        Theme theme = new Theme("테마2", "지금까지 이런 테마는 없었다.", 20200);
        Long id = themeRepository.save(theme);

        // when, then
        assertThat(themeRepository.deleteThemeById(id)).isTrue();
    }
}