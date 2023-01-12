package nextstep.main.java.nextstep.repository;

import nextstep.main.java.nextstep.domain.Theme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class JdbcThemeRepositoryTest {

    public static final Long NON_EXIST_THEME_ID = 0L;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private JdbcThemeRepository jdbcThemeRepository;

    @BeforeEach
    void setUp() {
        jdbcThemeRepository = new JdbcThemeRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("테마 생성 테스트")
    void createTest() {
        Theme expectedTheme = new Theme("테마이름", "테마설명", 22000);
        Theme actualTheme = jdbcThemeRepository.save(expectedTheme);

        assertThat(actualTheme.getName()).isEqualTo(expectedTheme.getName());
        assertThat(actualTheme.getDesc()).isEqualTo(expectedTheme.getDesc());
        assertThat(actualTheme.getPrice()).isEqualTo(expectedTheme.getPrice());
    }

    @Test
    @DisplayName("테마 단건 조회 테스트")
    void findBydIdTest() {
        Theme savedTheme = jdbcThemeRepository.save(new Theme("테마이름", "테마설명", 22000));

        assertThat(jdbcThemeRepository.findById(savedTheme.getId())
                .get())
                .isEqualTo(savedTheme);
        assertThat(jdbcThemeRepository.findById(NON_EXIST_THEME_ID)
                .isEmpty()).isTrue();
    }

    @Test
    @DisplayName("테마 전체 조회 테스트")
    void findAllTest() {
        List<Theme> savedThemeList = List.of(jdbcThemeRepository.save(new Theme("테마1", "테마1", 22000)),
                jdbcThemeRepository.save(new Theme("테마2", "테마2", 0)),
                jdbcThemeRepository.save(new Theme("테마3", "테마3", 100)));

        List<Theme> foundThemeList = jdbcThemeRepository.findAll();

        assertThat(foundThemeList.get(0)).isEqualTo(savedThemeList.get(0));
        assertThat(foundThemeList.get(1)).isEqualTo(savedThemeList.get(1));
        assertThat(foundThemeList.get(2)).isEqualTo(savedThemeList.get(2));
    }

    @Test
    @DisplayName("테마 수정 기능 테스트")
    void updateTest() {
        Theme savedTheme = jdbcThemeRepository.save(new Theme("테마이름", "테마설명", 22000));
        Theme updatingTheme = new Theme(savedTheme.getId(), "테마이름 변경", "테마설명 변경", 0);

        assertThat(jdbcThemeRepository.update(updatingTheme)).isEqualTo(1);
        assertThat(jdbcThemeRepository.findById(updatingTheme.getId())
                .get()).isNotEqualTo(savedTheme);
        assertThat(jdbcThemeRepository.findById(updatingTheme.getId())
                .get()).isEqualTo(updatingTheme);
    }
}
