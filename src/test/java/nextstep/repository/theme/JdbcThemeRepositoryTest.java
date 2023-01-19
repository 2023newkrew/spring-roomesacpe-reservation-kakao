package nextstep.repository.theme;

import nextstep.domain.Theme;
import nextstep.repository.ResetTable;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@Transactional
public class JdbcThemeRepositoryTest {

    @Autowired
    JdbcThemeRepository jdbcThemeRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    ResetTable resetTable;
    private static Theme theme;

    @BeforeAll
    static void setUpTheme() {
        theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
    }

    @BeforeEach
    void setUp() {
        resetTable = new ResetTable(jdbcTemplate);
        resetTable.jdbcThemeReset();
    }

    @AfterEach
    void setUpTable() {
        resetTable.jdbcThemeReset();
    }

    @DisplayName("테마를 생성 할 수 있다.")
    @Test
    void createThemeTest() {
        assertDoesNotThrow(() -> jdbcThemeRepository.save(theme));
    }

    @DisplayName("테마를 삭제 할 수 있다.")
    @Test
    void deleteThemeTest() {
        jdbcThemeRepository.save(theme);
        Long id = jdbcThemeRepository.findByTheme(theme).getId();
        assertDoesNotThrow(() -> jdbcThemeRepository.deleteById(id));
    }

    @DisplayName("같은 테마를 중복으로 생성 할 수 없다.")
    @Test
    void duplicateThemeExceptionTest() {
        jdbcThemeRepository.save(theme);
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> jdbcThemeRepository.save(theme));
    }

    @DisplayName("전체 테마를 조회 할 수 있다.")
    @Test
    void findAllThemeTest() {
        jdbcThemeRepository.save(theme);
        assertThat(jdbcThemeRepository.findAll().size()).isEqualTo(1);
    }
}
