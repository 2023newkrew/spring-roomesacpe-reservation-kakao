package reservation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import reservation.domain.Theme;
import reservation.respository.ThemeRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Theme Repository Test")
@JdbcTest
public class ThemeRepositoryTest {
    private ThemeRepository themeRepository;
    private final Theme theme;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ThemeRepositoryTest() {
        theme = new Theme(null, "워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);
    }

    @BeforeEach
    void setUp() {
        themeRepository = new ThemeRepository(jdbcTemplate);
    }

    @AfterEach
    void setDown() {
        String[] sqls = {"DELETE FROM reservation", "DELETE FROM theme"};
        for (String sql : sqls) {
            jdbcTemplate.update(sql);
        }
    }

    @Test
    @DisplayName("테마 생성이 되어야 한다.")
    void save() {
        Long id = themeRepository.createTheme(theme);
        assertThat(id).isGreaterThan(0);
    }

    @Test
    @DisplayName("생성된 테마를 조회할 수 있어야 한다.")
    void find() {
        Long id = themeRepository.createTheme(theme);
        Theme that = themeRepository.getTheme(id);
        assertThat(that.getName()).isEqualTo(theme.getName());
    }

    @Test
    @DisplayName("생성된 테마가 예약이 없다면 삭제할 수 있어야 한다.")
    void delete() {
        Long id = themeRepository.createTheme(theme);
        int rowCount = themeRepository.deleteTheme(id);
        assertThat(rowCount).isEqualTo(1);
    }

    @Test
    @DisplayName("이름이 같은 테마는 생성이 불가능하다.")
    void duplicate() {
        Long id = themeRepository.createTheme(theme);
        Theme that = new Theme(null, theme.getName(), "TEST", 10_000);
        assertThatThrownBy(() -> themeRepository.createTheme(that));
    }
}
