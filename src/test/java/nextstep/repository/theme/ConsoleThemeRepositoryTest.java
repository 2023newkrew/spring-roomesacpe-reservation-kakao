package nextstep.repository.theme;

import nextstep.domain.Theme;
import nextstep.repository.ResetTable;
import nextstep.repository.reservation.ConsoleReservationRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ConsoleThemeRepositoryTest {

    ConsoleThemeRepository consoleThemeRepository = new ConsoleThemeRepository();
    ConsoleReservationRepository consoleReservationRepository = new ConsoleReservationRepository();

    private static Theme theme;

    @Autowired
    JdbcTemplate jdbcTemplate;

    ResetTable resetTable = new ResetTable(jdbcTemplate);

    @BeforeAll
    static void setUpTheme() {
        theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
    }

    @BeforeEach
    void setUp() {
        resetTable.consoleThemeTableReset();
    }

    @AfterEach
    void setUpTable() {
        resetTable.consoleThemeTableReset();
    }

    @DisplayName("테마를 생성 할 수 있다.")
    @Test
    void createThemeTest() {
        assertDoesNotThrow(() -> consoleThemeRepository.save(theme));
    }

    @DisplayName("테마를 삭제 할 수 있다.")
    @Test
    void deleteThemeTest() {
        consoleThemeRepository.save(theme);
        Long id = consoleThemeRepository.findByTheme(theme).getId();
        assertDoesNotThrow(() -> consoleThemeRepository.deleteById(id));
    }

    @DisplayName("같은 테마를 중복으로 생성 할 수 없다.")
    @Test
    void duplicateThemeExceptionTest() {
        consoleThemeRepository.save(theme);
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> consoleThemeRepository.save(theme));
    }

    @DisplayName("전체 테마를 조회 할 수 있다.")
    @Test
    void findAllThemeTest() {
        consoleThemeRepository.save(theme);
        assertDoesNotThrow(() -> consoleThemeRepository.findAll());
    }
}
