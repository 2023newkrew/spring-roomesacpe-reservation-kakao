package nextstep.repository.reservation;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.repository.reservation.JdbcReservationRepository;
import nextstep.repository.theme.JdbcThemeRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@Transactional
class JdbcReservationRepositoryTest {

    @Autowired
    JdbcReservationRepository jdbcReservationRepository;
    @Autowired
    JdbcThemeRepository jdbcThemeRepository;

    static Theme theme;

    @BeforeAll
    static void setUpTheme() {
        theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
    }

    @BeforeEach
    void setUp() {
        jdbcReservationRepository.dropTable();
        jdbcReservationRepository.createTable();
        jdbcThemeRepository.save(theme);

        theme = jdbcThemeRepository.findByTheme(theme);
    }

    @AfterEach
    void setUpTable() {
        jdbcReservationRepository.dropTable();
        jdbcReservationRepository.createTable();
        jdbcThemeRepository.dropThemeTable();
        jdbcThemeRepository.createThemeTable();
    }

    @DisplayName("예약을 저장할 수 있다.")
    @Test
    void jdbcAddReservationTest() {
        Reservation actual = new Reservation(
                1L, LocalDate.parse("2022-01-02"), LocalTime.parse("13:00"), "bryan", theme.getId());

        assertDoesNotThrow(() -> jdbcReservationRepository.save(actual.getDate(),
                actual.getTime(),
                actual.getName(),
                theme
        ));
    }

    @DisplayName("중복 예약은 예약할 수 없다.")
    @Test
    void jdbcAddReservationExceptionTest() {
        Reservation actual = new Reservation(
                1L, LocalDate.parse("2022-01-02"), LocalTime.parse("13:00"), "bryan", theme.getId());

        assertDoesNotThrow(() -> jdbcReservationRepository.save(actual.getDate(),
                        actual.getTime(),
                        actual.getName(),
                        theme
                )
        );

        assertThatExceptionOfType(Exception.class).isThrownBy(() ->
                jdbcReservationRepository.save(actual.getDate(),
                        actual.getTime(),
                        actual.getName(),
                        theme
                )
        );
    }

    @DisplayName("예약을 조회할 수 있다.")
    @Test
    void jdbcFindReservationTest() {
        //given
        Reservation actual = new Reservation(
                1L, LocalDate.parse("2022-01-02"), LocalTime.parse("13:00"), "bryan", theme.getId());

        Long saveId = jdbcReservationRepository.save(actual.getDate(),
                actual.getTime(),
                actual.getName(),
                theme
        );

        //when
        Reservation reservation2 = jdbcReservationRepository.findById(saveId);

        //then
        assertThat(actual.getName()).isEqualTo(reservation2.getName());
        assertThat(actual.getDate()).isEqualTo(reservation2.getDate());
    }

    @DisplayName("예약을 삭제할 수 있다.")
    @Test
    void jdbcDeleteReservationTest() {
        //given
        Reservation actual = new Reservation(
                1L, LocalDate.parse("2022-01-02"), LocalTime.parse("13:00"), "bryan", theme.getId());

        Long saveId = jdbcReservationRepository.save(actual.getDate(),
                actual.getTime(),
                actual.getName(),
                theme
        );

        assertDoesNotThrow(() ->
                jdbcReservationRepository.deleteById(saveId)
        );

        assertThatExceptionOfType(Exception.class).isThrownBy(() ->
                jdbcReservationRepository.findById(saveId)
        );
    }

}